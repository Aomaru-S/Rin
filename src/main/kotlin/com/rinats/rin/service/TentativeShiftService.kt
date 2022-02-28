package com.rinats.rin.service

import com.rinats.rin.controller.storemanager.TentativeShiftController
import com.rinats.rin.model.other.TentativeShiftData2
import com.rinats.rin.model.table.Shift
import com.rinats.rin.model.table.TentativeShift
import com.rinats.rin.model.table.TentativeShiftDetail
import com.rinats.rin.model.table.compositeId.TentativeShiftDetailId
import com.rinats.rin.model.table.compositeId.TentativeShiftId
import com.rinats.rin.repository.*
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.domain.Sort
import org.springframework.stereotype.Service
import java.time.LocalDate
import java.time.ZoneId
import java.util.*


@Service
class TentativeShiftService(
    @Autowired
    private val tentativeShiftRepository: TentativeShiftRepository,
    private val shiftRepository: ShiftRepository,
    private val employeeService: EmployeeService,
    private val roleRepository: RoleRepository,
    private val tentativeShiftDetailRepository: TentativeShiftDetailRepository,
    private val employeeLaborRepository: EmployeeLaborRepository,
    private val shiftTemplateRepository: ShiftTemplateRepository,

    //必要なデータ
    private val totalSalaryRepository: TotalSalaryRepository,
    private val settingsRepository: SettingsRepository,

    //設定キーない場合
    private val setSettingValueInDBService: SetSettingValueInDBService,

    //祝日Api
    private val getHolidaysJpApiService: GetHolidaysJpApiService
) {

    fun getTentativeShiftList(): List<TentativeShift> =
        tentativeShiftRepository.findAll(Sort.by(Sort.Direction.ASC, "id"))

    fun submitTentativeShift() {

        val tentativeShiftList = tentativeShiftRepository.findAll()
        tentativeShiftList.forEach {
            val shift = Shift(
                Date.from(it.id?.shiftDate?.atStartOfDay(ZoneId.systemDefault())?.toInstant()),
                it?.id?.employee?.id ?: return@forEach
            )
            shiftRepository.save(shift)
        }
    }

    fun deleteAll() {
        tentativeShiftRepository.deleteAll()
    }


    fun editAttendance(changeAttendanceMap: MutableMap<String, MutableList<TentativeShiftController.AttendanceDay>>) {
        var localDate: LocalDate = LocalDate.now()
//        val dayShiftMap2 = mutableMapOf<LocalDate, MutableList<String>>()
        val changeDaySet = mutableSetOf<LocalDate>()
        changeAttendanceMap.forEach {
            it.value.forEach { value ->
                val now = LocalDate.now()
                now.plusMonths(1)

                val ld = LocalDate.of(now.year, now.month, value.day)
                changeDaySet.add(ld)
            }
        }

        /*changeAttendanceMap.forEach {
            it.value.forEach { value ->
                val day = value.day
                val now = LocalDate.now()
                now.plusMonths(1)
                val ld = LocalDate.of(now.year, now.month, day)
                if (dayShiftMap2[ld] == null) {
                    dayShiftMap2[ld] = mutableListOf()
                }
                dayShiftMap2[ld]?.add(it.key)
            }
        }*/
        changeAttendanceMap.forEach topScope@{ map ->
            val employeeIdList = mutableListOf<String>()
            val employeeId = map.key
            map.value.forEach {
                if (it.isAttendance) {
                    var now = LocalDate.now()
                    now = now.plusMonths(1)
                    localDate = LocalDate.of(now.year, now.month, it.day)
                    val employee = employeeService.getEmployee(employeeId, containerManager = false) ?: return@topScope
                    val role = roleRepository.findById(2).orElse(null)
                    tentativeShiftRepository.save(
                        TentativeShift(
                            TentativeShiftId(
                                localDate,
                                employee
                            ),
                            role
                        )
                    )
                } else {
                    var now = LocalDate.now()
                    println(now)
                    now = now.plusMonths(1)
                    println(now)
                    localDate = LocalDate.of(now.year, now.month, it.day)
                    val employee = employeeService.getEmployee(employeeId, containerManager = false) ?: return@topScope
                    println("---------------------------")
                    println("localdate: $localDate")
                    println("employee: ${employee.id}")
                    tentativeShiftRepository.deleteById(
                        TentativeShiftId(
                            localDate,
                            employee
                        )
                    )
                }
            }
        }

        val tentativeShiftDetailList: MutableList<TentativeShiftDetail> = mutableListOf()
        val tentativeShiftList3 = mutableListOf<TentativeShift>()

        changeDaySet.forEach {
            val ld = it.plusMonths(1)
            val temp = tentativeShiftRepository.findById_ShiftDate(ld)
            if (temp.isEmpty()) {
                val pair = makeTentativeShiftPair(
                    TentativeShiftData2(
                        mutableListOf(),
                        ld,
                        1,
                        isLaborInsufficient = true,
                        isNumOfPeopleInsufficient = true
                    )
                )
                tentativeShiftDetailList.add(pair.second)
            }
            tentativeShiftList3.addAll(temp)
        }
        val tentativeShiftMap = tentativeShiftList3.groupBy({ it.id?.shiftDate!! }, { it.id?.employee?.id })

        val calendar = Calendar.getInstance()
        val templateList = shiftTemplateRepository.findAll()
        val holidaysJpMap =
            getHolidaysJpApiService.getHolidaysJpApi(calendar.get(Calendar.YEAR)) ?: throw NullPointerException("")


        tentativeShiftMap.forEach { map ->
            calendar.time = Date.from(map.key.atStartOfDay(ZoneId.systemDefault()).toInstant())
            val calendarLocalDate = calendar.time.toInstant().atZone(ZoneId.systemDefault()).toLocalDate()
            val template = templateList.filter { it.id?.roleId == 1 }
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
                    else -> throw IllegalStateException("unknown error!!!")
                }
            }!!

            //全ての組み合わせ
            val combinationList = callCombination(map.value.toMutableList(), people)

            //従業員不足
            if (combinationList.isEmpty()) {
                val pair = makeTentativeShiftPair(
                    TentativeShiftData2(
                        map.value.toMutableList(),
                        map.key,
                        1,
                        isLaborInsufficient = true,
                        isNumOfPeopleInsufficient = true
                    )
                )
                tentativeShiftDetailList.add(pair.second)
            } else {
                val pair = makeTentativeShiftPair(
                    TentativeShiftData2(
                        map.value.toMutableList(),
                        map.key,
                        1,
                        isLaborInsufficient = false,
                        isNumOfPeopleInsufficient = false
                    )
                )
                tentativeShiftDetailList.add(pair.second)
            }
        }

        tentativeShiftDetailRepository.saveAll(tentativeShiftDetailList)


        //--------------------------------------------------------------------------------------------------------------

        /*val calendar = Calendar.getInstance()
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

        val tentativeShiftList2 = tentativeShiftRepository.findAll()
        val totalSalaryList = totalSalaryRepository.findAll().filter { calendar.get(Calendar.YEAR) == it.id?.year }
        val holidaysJpMap =
            getHolidaysJpApiService.getHolidaysJpApi(calendar.get(Calendar.YEAR)) ?: throw NullPointerException("")

        //従業員と役職の連関エンティティ取得
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
        val tentativeShiftMap = tentativeShiftList2.groupBy({ it.id?.shiftDate!! }, { it.id?.employee?.id }).toSortedMap()

        //仮シフト作成
        val employeeToSalaryMap: MutableMap<Employee, Int> = mutableMapOf()
        employeeList.forEach { employeeToSalaryMap[it] = 0 }

        val tentativeShiftDetailList: MutableList<TentativeShiftDetail> = mutableListOf()

        tentativeShiftMap.forEach { (date, employeeIdList) ->
            calendar.time = Date.from(date.atStartOfDay(ZoneId.systemDefault()).toInstant())
            val calendarLocalDate = calendar.time.toInstant().atZone(ZoneId.systemDefault()).toLocalDate()
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
                        val employee = employeeList.singleOrNull { it.id == employeeId } ?: return@loop3
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
                    tentativeShiftDetailList.add(pair.second)
                    return@loop1
                }
            }
        }
        tentativeShiftDetailRepository.saveAll(tentativeShiftDetailList)
        //--------------------------------------------------------------------------------------------------------------*/

    }

    /*fun makeTentativeShiftPair(tentativeShiftData: TentativeShiftData): Pair<MutableList<TentativeShift>, TentativeShiftDetail> {
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
    }*/

    fun makeTentativeShiftPair(tentativeShiftData: TentativeShiftData2): Pair<MutableList<TentativeShift>, TentativeShiftDetail> {
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