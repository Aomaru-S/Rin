package com.rinats.rin.service

import com.rinats.rin.model.table.compositeId.TentativeShiftDetailId
import com.rinats.rin.annotation.NonAuth
import com.rinats.rin.model.table.Employee
import com.rinats.rin.model.table.TentativeShift
import com.rinats.rin.model.other.TentativeShiftData
import com.rinats.rin.model.table.TentativeShiftDetail
import com.rinats.rin.model.table.compositeId.TentativeShiftId
import com.rinats.rin.repository.*
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import java.time.ZoneId
import java.util.*

@Service
class ShiftGeneratorService(
    @Autowired
    //必要なデータ
    private val employeeService: EmployeeService,
    private val roleRepository: RoleRepository,
    private val employeeLaborRepository: EmployeeLaborRepository,
    private val shiftTemplateRepository: ShiftTemplateRepository,
    private val shiftHopeRepository: ShiftHopeRepository,
    private val totalSalaryRepository: TotalSalaryRepository,
    private val settingsRepository: SettingsRepository,

    //出力先
    private val tentativeShiftRepository: TentativeShiftRepository,
    private val tentativeShiftDetailRepository: TentativeShiftDetailRepository,

    //設定キーない場合
    private val setSettingValueInDBService: SetSettingValueInDBService,

    //祝日Api
    private val getHolidaysJpApiService: GetHolidaysJpApiService
) {
    //1日の労働時間がいる
    //private val workingHours = 6
    //給与関係のテーブルがないので仮データ
    //private val taxable = 1000000 //100万円 課税対象額
    @NonAuth
    fun shiftGenerator() {
        val calendar = Calendar.getInstance()
        calendar.timeZone = TimeZone.getTimeZone(ZoneId.systemDefault())
        calendar.isLenient = false

        //翌月の初日と最終日を取得
        when (calendar.get(Calendar.MONTH) == Calendar.DECEMBER) {
            true -> {
                calendar.set(Calendar.YEAR, calendar.get(Calendar.YEAR) + 1)
                calendar.set(Calendar.MONTH, Calendar.JANUARY)
            }
            false -> {
                calendar.set(Calendar.YEAR, calendar.get(Calendar.YEAR))
                calendar.set(Calendar.MONTH, calendar.get(Calendar.MONTH) + 1)
            }
        }
        calendar.set(Calendar.DATE, 1)
        val startDate = calendar.time.toInstant().atZone(ZoneId.systemDefault()).toLocalDate()
        var date = 28
        do {
            calendar.set(Calendar.DATE, ++date)
            val result = runCatching { calendar.time }
        } while (result.isSuccess)
        calendar.set(Calendar.DATE, --date)
        val lastDate = calendar.time.toInstant().atZone(ZoneId.systemDefault()).toLocalDate()

        val shiftHopeList = shiftHopeRepository.findById_ShiftDateBetween(startDate, lastDate).filter {
            if (it.id?.employee?.isTentative == true) return@filter false
            if (it.id?.employee?.isRetirement == true) return@filter false
            true
        }
        val totalSalaryList = totalSalaryRepository.findAll().filter { calendar.get(Calendar.YEAR) == it.id?.year }
        val holidaysJpMap =
            getHolidaysJpApiService.getHolidaysJpApi(calendar.get(Calendar.YEAR)) ?: throw NullPointerException("")

        //従業員と役職の連関エンティティ取得
//        val employeeLaborList = employeeLaborRepository.findAll()
        val employeeLaborList = employeeLaborRepository.findAll().filter { it.id?.roleId != 0 && it.id?.roleId != 2}
        val employeeList = employeeService.getEmployeeList(containerManager = false)
        val templateList = shiftTemplateRepository.findAll()

        if (setSettingValueInDBService.isKeysNull()) setSettingValueInDBService.makeKeys()

        val settingKeys = settingsRepository.findAll()
        val workingHours = Integer.parseInt(settingKeys.single { it.id == "working_hours" }.settingValue)
        val taxable = Integer.parseInt(settingKeys.single { it.id == "taxable" }.settingValue)

        val roleIdToEmployeeLaborListMap = employeeLaborList.groupBy { it.id?.roleId!! }

        //閾値を業種ごとに算出
        val medianMap: SortedMap<Int, Double> = sortedMapOf()
        roleIdToEmployeeLaborListMap.forEach { (roleId, employeeLaborList) ->
            //平均値
            var total = 0
            employeeLaborList.forEach { total += it.labor!! }
            val average = total / employeeLaborList.size.toDouble()

            //中央値
            var laborArray = arrayOf<Int>()
            employeeLaborList.forEach {
                laborArray += 1
                laborArray[laborArray.lastIndex] = it.labor!!
            }
            Arrays.sort(laborArray)
            val central = laborArray.size / 2
            val median = when (0 != (laborArray.size % 2)) {
                true -> laborArray[central].toDouble()
                false -> laborArray[central - 1] + laborArray[central] / 2.0
            }

            //平均値か中央値の低い方を閾値にする
            medianMap[roleId] = when (average < median) {
                true -> average
                false -> median
            }
        }

        //シフト希望を日付ごとに
        val shiftHopeMap = shiftHopeList.groupBy({ it.id?.shiftDate!! }, { it.id?.employee?.id }).toSortedMap()

        //仮シフト作成
        val employeeToSalaryMap: MutableMap<Employee, Int> = mutableMapOf()
        employeeList.forEach { employeeToSalaryMap[it] = 0 }

        val tentativeShiftList: MutableList<TentativeShift> = mutableListOf()
        val tentativeShiftDetailList: MutableList<TentativeShiftDetail> = mutableListOf()

        shiftHopeMap.forEach { (date, employeeIdList) ->
            calendar.time = Date.from(date.atStartOfDay(ZoneId.systemDefault()).toInstant())
            val calendarLocalDate = calendar.time.toInstant().atZone(ZoneId.systemDefault()).toLocalDate()

            val tempTentativeShiftList: MutableList<TentativeShift> = mutableListOf()

            medianMap.forEach loop1@{ (roleId, median) ->
                val template = templateList.filter { it.id?.roleId == roleId }
                val people = when (holidaysJpMap.containsKey(calendarLocalDate)) {
                    true -> template.single { it.id?.weeksHolidayName == "holiday" }.numOfPeople
                    false -> when (calendar.get(Calendar.DAY_OF_WEEK)) {
                        Calendar.SUNDAY -> template.single { it.id?.weeksHolidayName.equals("sunday") }.numOfPeople
                        Calendar.MONDAY -> template.single { it.id?.weeksHolidayName.equals("monday") }.numOfPeople
                        Calendar.TUESDAY -> template.single { it.id?.weeksHolidayName.equals("tuesday") }.numOfPeople
                        Calendar.WEDNESDAY -> template.single { it.id?.weeksHolidayName.equals("wednesday") }.numOfPeople
                        Calendar.THURSDAY -> template.single { it.id?.weeksHolidayName.equals("thursday") }.numOfPeople
                        Calendar.FRIDAY -> template.single { it.id?.weeksHolidayName.equals("friday") }.numOfPeople
                        Calendar.SATURDAY -> template.single { it.id?.weeksHolidayName.equals("saturday") }.numOfPeople
                        else -> throw IllegalArgumentException("unknown error!!!")
                    }
                }!!

                if (people < 0) return@loop1

                //filter内容 → employeeIdからroleに当てはまるemployeeをemployeeLaborMapから検索する
                val subject: MutableList<String> = mutableListOf()
                employeeIdList.forEach { employeeId ->
                    roleIdToEmployeeLaborListMap.getValue(roleId).filter { employeeLaborList ->
                        employeeId == employeeLaborList.id?.employeeId
                    }.forEach {
                        subject.add(it.id?.employeeId!!)
                    }
                }

                //全ての組み合わせ
                val combinationList = callCombination(subject, people)

                //従業員不足
                if (combinationList.isEmpty()) {
                    val pair = makeTentativeShiftPair(
                        TentativeShiftData(
                            subject, date, roleId, isLaborInsufficient = true, isNumOfPeopleInsufficient = true
                        )
                    )
                    tempTentativeShiftList.addAll(pair.first)
                    tentativeShiftDetailList.add(pair.second)
                    return@loop1
                }

                //必要な労働力の合計
                val totalLabor = median * people

                //totalLaborを超え年内の給料合計が課税対象額以内の組み合わせ
                val upTotalLaborList: MutableList<MutableList<String>> = mutableListOf()
                combinationList.forEach loop2@{ combination ->
                    var labor = 0
                    combination.forEach loop3@{ employeeId ->
//                        val employee = employeeList.single { it.id == employeeId }
//                        val employee = employeeList.singleOrNull { it.id == employeeId }
                        val employee = employeeList.singleOrNull { it.id == employeeId } ?: return@loop3
//                        if (employee.isTaxableOk!!) {
                        if (employee.isTaxableOk == true) {
                            labor += roleIdToEmployeeLaborListMap.getValue(roleId)
                                .single { employeeId == it.id?.employeeId }.labor!!
                            return@loop3
                        }
                        val onCalcSalary = employeeToSalaryMap.getValue(employee)
                        val pastSalary = totalSalaryList.single { employeeId == it.id?.employeeId }.salary!!
                        val nowSalary = pastSalary + onCalcSalary
                        when (taxable < nowSalary) {
                            true -> return@loop2
                            false -> labor += roleIdToEmployeeLaborListMap.getValue(roleId)
                                .single { employeeId == it.id?.employeeId }.labor!!
                        }
                    }
                    if (totalLabor <= labor && combination.size == people) upTotalLaborList.add(combination)
                }

                //労働力不足
                if (upTotalLaborList.isEmpty()) {
                    val pair = makeTentativeShiftPair(
                        TentativeShiftData(
                            subject, date, roleId, isLaborInsufficient = true, isNumOfPeopleInsufficient = false
                        )
                    )
                    tempTentativeShiftList.addAll(pair.first)
                    tentativeShiftDetailList.add(pair.second)
                    return@loop1
                }

                //合計給与がが一番少なく、役職ごとに被りがない組み合わせ
                upTotalLaborList.sortBy { combination ->
                    combination.sumOf { employeeId ->
                        val onCalcSalary = employeeToSalaryMap.getValue(employeeList.single { it.id == employeeId })
                        val pastSalary = totalSalaryList.single { employeeId == it.id?.employeeId }.salary!!
                        onCalcSalary + pastSalary
                    }
                }

                val fixedCombination: MutableList<String> = mutableListOf()
                run run1@{
                    upTotalLaborList.forEach { combination ->
                        if (combination.intersect(tempTentativeShiftList.groupBy { it.id?.employee?.id }.keys)
                                .isEmpty()
                        ) {
                            fixedCombination.addAll(combination)
                            return@run1
                        }
                    }
                }

                if (fixedCombination.isEmpty()) {
                    val pair = makeTentativeShiftPair(
                        TentativeShiftData(
                            subject, date, roleId, isLaborInsufficient = true, isNumOfPeopleInsufficient = true
                        )
                    )
                    tempTentativeShiftList.addAll(pair.first)
                    tentativeShiftDetailList.add(pair.second)
                    return@loop1
                }

                fixedCombination.forEach { employeeId ->
                    val employee = employeeList.single { it.id == employeeId }
                    employeeToSalaryMap.replace(
                        employee, employee.hourlyWage!! * workingHours
                    )
                }

                val pair = makeTentativeShiftPair(
                    TentativeShiftData(
                        fixedCombination, date, roleId, isLaborInsufficient = false, isNumOfPeopleInsufficient = false
                    )
                )
                tempTentativeShiftList.addAll(pair.first)
                tentativeShiftDetailList.add(pair.second)
            }
            tentativeShiftList.addAll(tempTentativeShiftList)
        }
        tentativeShiftRepository.saveAll(tentativeShiftList)
        tentativeShiftDetailRepository.saveAll(tentativeShiftDetailList)
    }

    fun makeTentativeShiftPair(tentativeShiftData: TentativeShiftData): Pair<MutableList<TentativeShift>, TentativeShiftDetail> {
        val saveSiftList: MutableList<TentativeShift> = mutableListOf()
        tentativeShiftData.apply {
            tentativeShiftData.employeeIdList.forEach {
//                val employee = employeeService.getEmployee(it) ?: throw IllegalStateException("employee is null")
                val employee = employeeService.getEmployee(it) ?: return@forEach
                val saveShiftId = TentativeShiftId(shiftDate, employee)
                val saveShift = TentativeShift(saveShiftId, roleRepository.getById(roleId))
                saveSiftList.add(saveShift)
            }

            val saveShiftDetailId = TentativeShiftDetailId(shiftDate, roleId)
            val saveShiftDetail =
                TentativeShiftDetail(saveShiftDetailId, isLaborInsufficient, isNumOfPeopleInsufficient)
            return saveSiftList to saveShiftDetail
        }
    }

    fun <T> callCombination(elementList: MutableList<T>, selected: Int): MutableList<MutableList<T>> {
        val elementListList: MutableList<MutableList<T>> = mutableListOf()
        elementList.forEach {
            elementListList.add(mutableListOf(it))
        }
        return combination(elementListList, selected)
    }

    private fun <T> combination(
        elementListList: MutableList<MutableList<T>>,
        selected: Int
    ): MutableList<MutableList<T>> {
        val combinationListList: MutableList<MutableList<T>> = mutableListOf()
        return when (1 < selected) {
            true -> {
                elementListList.withIndex().forEach { elementList ->
                    combination(
                        elementListList.subList(elementList.index + 1, elementListList.size),
                        selected - 1
                    ).forEach {
                        combinationListList.add((elementList.value + it) as MutableList<T>)
                    }
                }
                combinationListList
            }
            false -> elementListList
        }
    }
}