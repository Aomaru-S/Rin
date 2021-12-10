package com.rinats.rin.service

import com.rinats.rin.model.Employee
import com.rinats.rin.model.Role
import com.rinats.rin.model.TentativeShift
import com.rinats.rin.model.TentativeShiftDetail
import com.rinats.rin.repository.*
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import java.lang.IllegalArgumentException
import java.util.*

@Service
class ShiftGeneratorService(
    @Autowired
    private val employeeRepository: EmployeeRepository,
    private val roleRepository: RoleRepository,
    private val laborRepository: LaborRepository,
    private val shiftTemplateRepository: ShiftTemplateRepository,
    private val shiftHopeRepository: ShiftHopeRepository,
    private val tentativeShiftRepository: TentativeShiftRepository,
    private val tentativeShiftDetailRepository: TentativeShiftDetailRepository
) {

    //給与関係のテーブルがないので仮データ
    private val taxable = 1000000 //100万円 課税対象額
    private val salary = 0 //今までの合計給与

    fun shiftGenerator() {
        val calendar = Calendar.getInstance()
        calendar.timeZone = TimeZone.getTimeZone("Asia/Tokyo")
        calendar.isLenient = false
        calendar.timeInMillis = 0

        //翌月の1日目を取得
        calendar.set(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH) + 2, 1)

        //翌月の日毎の配列生成
        val dateList: MutableList<Date> = mutableListOf()
        var date = 0
        do {
            calendar.set(Calendar.DATE, date++)
            val result = runCatching { dateList.add(calendar.time) }
        } while (result.isSuccess)

        //従業員と役職の連関エンティティ取得
        val employeeList = employeeRepository.findAll()
        val roleList = roleRepository.findAll()
        val laborList = laborRepository.findAll()
        val employeeRoleMap = laborList.groupBy(
            { employeeRole -> roleList.single { employeeRole.id?.roleId.equals(it.roleId) } },
            { employeeRole -> employeeList.single { employeeRole.id?.employeeId.equals(it.employeeId) } }
        )

        //中央値を従業員のグループごとに算出
        val medianMap: MutableMap<Role, Double> = mutableMapOf()
        employeeRoleMap.forEach { (role, employeeList) ->
            var laborArray = arrayOf<Int>()
            employeeList.forEach { employee ->
                val labor = laborList.single { labor ->
                    employee.employeeId == labor.id?.employeeId && role.roleId == labor.id?.roleId
                }
                laborArray += 1
                laborArray[laborArray.lastIndex] = labor.level ?: throw NullPointerException("unknown error!!!")
            }
            Arrays.sort(laborArray)
            val central = laborArray.size / 2
            val median = when (0 != (laborArray.size % 2)) {
                true -> laborArray[central].toDouble()
                false -> laborArray[central - 1] + laborArray[central] / 2.0
            }
            medianMap[role] = median
        }

        //シフト希望を日付ごとに
        val dateFirst = dateList.first() as java.sql.Date
        val dateLast = dateList.last() as java.sql.Date
        val shiftHopeList = shiftHopeRepository.findByDate(dateFirst, dateLast)
        val shiftHopeMap = shiftHopeList.groupBy(
            { it.date },
            { shiftHope -> employeeList.single { shiftHope.employeeId == it.employeeId } }
        )

        //仮シフト作成
        val templateList = shiftTemplateRepository.findAll()
        val tentativeShiftMap: MutableMap<Role, TentativeShift> = mutableMapOf()
        shiftHopeMap.forEach { (date, shiftHopeList) ->
            //祝日に対する処理がない、曜日に対する定数をどうするか？、仮の定数を入れてます
            calendar.time = date
            for ((role, median) in medianMap) {
                val template = templateList.filter { it.id?.roleId == role.roleId }
                val people: Int = when (calendar.get(Calendar.DAY_OF_WEEK)) {
                    Calendar.SUNDAY -> template.single { it.id?.dayOfWeek == "日曜日" }.numOfPeople
                    Calendar.MONDAY -> template.single { it.id?.dayOfWeek == "月曜日" }.numOfPeople
                    Calendar.TUESDAY -> template.single { it.id?.dayOfWeek == "火曜日" }.numOfPeople
                    Calendar.WEDNESDAY -> template.single { it.id?.dayOfWeek == "水曜日" }.numOfPeople
                    Calendar.THURSDAY -> template.single { it.id?.dayOfWeek == "木曜日" }.numOfPeople
                    Calendar.FRIDAY -> template.single { it.id?.dayOfWeek == "金曜日" }.numOfPeople
                    Calendar.SATURDAY -> template.single { it.id?.dayOfWeek == "土曜日" }.numOfPeople
                    else -> throw IllegalArgumentException("unknown error!!!")
                } ?: throw NullPointerException("unknown error!!!")

                if (people < 0) continue

                //必要な労働力の合計
                val totalLabor = median * people

                //filter内容 → shiftHopeListからroleに当てはまるemployeeをemployeeRoleMapから検索する
                val subject: MutableList<Employee> = mutableListOf()
                shiftHopeList.forEach { shiftHope ->
                    subject.addAll(employeeRoleMap.getValue(role).filter { shiftHope.employeeId == it.employeeId })
                }

                //全ての組み合わせ
                val combinationList = callCombination(subject, people)

                //従業員不足
                if (combinationList.isEmpty()) {
                    //処理未完成
                    val tentativeShiftDetail = TentativeShiftDetail()
                    tentativeShiftDetail.id = date.toLocalDate()
                    tentativeShiftDetail.isEmployeeInsufficient = true
                    continue
                }

                //totalLaborを超える組み合わせ
                val upTotalLaborList: MutableList<MutableList<Employee>> = mutableListOf()
                label1@ for (combination in combinationList) {
                    var labor = 0
                    for (employee in combination) {
                        labor += when (taxable < salary) {
                            true -> continue@label1
                            false -> laborList.single { employee.employeeId == it.id?.employeeId && role.roleId == it.id?.roleId }.level
                                ?: throw NullPointerException("unknown error!!!")
                        }
                        if (totalLabor <= labor) upTotalLaborList.add(combination)
                    }
                }

                //労働力不足
                if (upTotalLaborList.isEmpty()) {
                    //処理未完成
                    val tentativeShiftDetail = TentativeShiftDetail()
                    tentativeShiftDetail.id = date.toLocalDate()
                    tentativeShiftDetail.isLaborInsufficient = true
                    continue
                }

                //合計給与がが一番少ない組み合わせ
                val fixedCombination: MutableList<Employee> = mutableListOf()
                var minSalary = people * taxable
                upTotalLaborList.forEach { combination ->
                    var salary = 0
                    combination.forEach { _ ->
                        salary += this.salary
                    }
                    if (salary < minSalary) {
                        minSalary = salary
                        fixedCombination.removeAll(fixedCombination)
                        fixedCombination.addAll(combination)
                    }
                }
                fixedCombination.forEach {
                    tentativeShiftRepository.save(TentativeShift(date, it.employeeId))
                }
                val tentativeShiftDetail = TentativeShiftDetail()
                tentativeShiftDetail.id = date.toLocalDate()
                tentativeShiftDetail.isLaborInsufficient = false
                tentativeShiftDetail.isEmployeeInsufficient = false
                tentativeShiftDetailRepository.save(tentativeShiftDetail)
            }
        }
    }

    fun callCombination(employeeList: MutableList<Employee>, selected: Int): MutableList<MutableList<Employee>> {
        val combinationList: MutableList<MutableList<Employee>> = mutableListOf()
        employeeList.forEach {
            combinationList.add(mutableListOf(it))
        }
        return combination(combinationList, selected)
    }

    fun combination(
        employeeList: MutableList<MutableList<Employee>>,
        selected: Int
    ): MutableList<MutableList<Employee>> {
        val combinationList: MutableList<MutableList<Employee>> = mutableListOf()
        return when (1 < selected) {
            true -> {
                employeeList.withIndex().forEach { employee ->
                    combination(employeeList.subList(employee.index + 1, employeeList.size), selected - 1).forEach {
                        combinationList.add((employee.value + it) as MutableList<Employee>)
                    }
                }
                combinationList
            }
            false -> employeeList
        }
    }
}