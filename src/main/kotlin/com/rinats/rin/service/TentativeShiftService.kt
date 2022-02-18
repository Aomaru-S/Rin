package com.rinats.rin.service

import com.rinats.rin.controller.storemanager.TentativeShiftController
import com.rinats.rin.model.table.Shift
import com.rinats.rin.model.table.TentativeShift
import com.rinats.rin.model.table.compositeId.TentativeShiftId
import com.rinats.rin.repository.RoleRepository
import com.rinats.rin.repository.ShiftRepository
import com.rinats.rin.repository.TentativeShiftRepository
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
    private val roleRepository: RoleRepository
) {

    fun getTentativeShiftList(): List<TentativeShift> = tentativeShiftRepository.findAll(Sort.by(Sort.Direction.ASC, "id"))

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

    fun editAttendance(changeAttendance: MutableMap<String, MutableList<TentativeShiftController.AttendanceDay>>) {
        changeAttendance.forEach topScope@{ map ->
            val employeeId = map.key
            map.value.forEach {
                if (it.isAttendance) {
                    var now = LocalDate.now()
                    now = now.plusMonths(1)
                    val localDate = LocalDate.of(now.year, now.month, it.day)
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
                    val localDate = LocalDate.of(now.year, now.month, it.day)
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
    }
}