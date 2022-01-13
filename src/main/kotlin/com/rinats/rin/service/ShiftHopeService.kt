package com.rinats.rin.service

import com.rinats.rin.model.ShiftHope
import com.rinats.rin.model.compositeKey.ShiftHopeId
import com.rinats.rin.model.form.ShiftHopeForm
import com.rinats.rin.model.response.ShiftHopeResponse
import com.rinats.rin.repository.ShiftHopeRepository
import com.rinats.rin.util.DateUtil
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import java.sql.Date
import java.text.ParseException
import java.time.ZoneId
import java.util.*
import kotlin.collections.ArrayList

@Service
class ShiftHopeService(
    @Autowired
    val shiftHopeRepository: ShiftHopeRepository
) {
    fun getShiftHope(employeeId: String, year: Int, month: Int): ShiftHopeResponse {
        val shiftHopes = shiftHopeRepository.findById_EmployeeId(employeeId)
        val days = ArrayList<Int>()
        shiftHopes.forEach {
            val calendar = Calendar.getInstance()
            calendar.timeInMillis = Date.from(it.id?.shiftDate?.atStartOfDay(ZoneId.systemDefault())?.toInstant()).time
            val y = calendar.get(Calendar.YEAR)
            val m = calendar.get(Calendar.MONTH)
            if (year == y && month == m) {
                days.add(calendar.get(Calendar.DAY_OF_MONTH))
            }
        }

        return ShiftHopeResponse(year, month, days)
    }

    fun submitShift(shiftHopeForm: ShiftHopeForm, employeeId: String): Boolean {
        val year = shiftHopeForm.year
        val month = shiftHopeForm.month

        shiftHopeForm.days.forEach { day ->
            val date = try {
                DateUtil.getDate(year, month, day)
            } catch (e: ParseException) {
                return false
            }

            val shiftHopeId = ShiftHopeId()
            shiftHopeId.shiftDate = date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate()
            shiftHopeId.employeeId = employeeId
            val shiftHope = ShiftHope()
            shiftHope.id = shiftHopeId
            shiftHopeRepository.save(shiftHope)
        }
        return true
    }

    fun deleteShiftHope(employeeId: String): Boolean {
        return shiftHopeRepository.deleteById_EmployeeId(employeeId)
    }
}