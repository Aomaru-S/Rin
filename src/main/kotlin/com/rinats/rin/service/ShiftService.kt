package com.rinats.rin.service

import com.rinats.rin.model.response.Shift
import com.rinats.rin.model.response.ShiftResponse
import com.rinats.rin.repository.AuthInfoRepository
import com.rinats.rin.repository.EmployeeRepository
import com.rinats.rin.repository.ShiftRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.domain.Sort
import org.springframework.stereotype.Service
import java.util.*

@Service
class ShiftService(
    @Autowired
    val shiftRepository: ShiftRepository,
    val employeeRepository: EmployeeRepository,
    val authInfoRepository: AuthInfoRepository
) {
    fun getAllShift(year: Int, month: Int): ShiftResponse? {
        val shifts = shiftRepository.findAll(Sort.by(Sort.Direction.ASC, "employeeId"))
        val days = ArrayList<ShiftDay>()
        shifts.forEach {
            val calendar = Calendar.getInstance()
            calendar.timeInMillis = it.date.time
            if (calendar.get(Calendar.YEAR) == year &&
                calendar.get(Calendar.MONTH).plus(1) == month
            ) {
                val employee = employeeRepository.findById(it.employeeId).orElse(null) ?: return null
                days.add(
                    ShiftDay(
                        calendar.get(Calendar.DAY_OF_MONTH),
                        employee.id ?: return null,
                        employee.firstName ?: "",
                        employee.lastName ?: ""
                    )
                )
            }
        }
        return ShiftResponse(year, month, days)
    }

    fun getShift(year: Int, month: Int, token: String): Shift? {
        val _month = month + 1
        val employeeId = authInfoRepository.findByAccessToken(token).get().employeeId
        val shifts = shiftRepository.findByEmployeeId(employeeId)
        val days = ArrayList<Int>()
        shifts.forEach {
            val calendar = Calendar.getInstance()
            calendar.timeInMillis = it.date.time
            if (calendar.get(Calendar.YEAR) == year &&
//                calendar.get(Calendar.MONTH) + 1 == month
                calendar.get(Calendar.MONTH) + 1 == _month
            ) {
                days.add(calendar.get(Calendar.DAY_OF_MONTH))
            }
        }
        return Shift(year, _month, days)
    }

    data class ShiftDay(
        val day: Int,
        val employeeId: String,
        val firstName: String,
        val lastName: String
    )
}