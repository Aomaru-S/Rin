package com.rinats.rin.service

import com.rinats.rin.model.response.ShiftResponse
import com.rinats.rin.repository.EmployeeRepository
import com.rinats.rin.repository.ShiftRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import java.util.*
import kotlin.collections.ArrayList

@Service
class ShiftService(
    @Autowired
    val shiftRepository: ShiftRepository,
    val employeeRepository: EmployeeRepository
) {
    fun getShift(year: Int, month: Int): ShiftResponse? {
        val shifts = shiftRepository.findAll()
        val days = ArrayList<ShiftDay>()
        shifts.forEach {
            val calendar = Calendar.getInstance()
            calendar.timeInMillis = it.date.time
            if (calendar.get(Calendar.YEAR) == year &&
                calendar.get(Calendar.MONTH) + 1 == month
            ) {
                val employee = employeeRepository.findById(it.employeeId).orElse(null) ?: return null
                days.add(ShiftDay(calendar.get(Calendar.DAY_OF_MONTH), employee.firstName, employee.lastName))
            }
        }
        return ShiftResponse(year, month, days)
    }

    data class ShiftDay(
        val day: Int,
        val firstName: String,
        val lastName: String
    )
}