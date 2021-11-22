package com.rinats.rin.service

import com.rinats.rin.model.ShiftHope
import com.rinats.rin.model.form.ShiftHopeForm
import com.rinats.rin.model.response.ShiftHopeResponse
import com.rinats.rin.repository.ShiftHopeRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import java.text.DateFormat
import java.text.ParseException
import java.text.SimpleDateFormat
import java.sql.Date
import java.util.*
import kotlin.collections.ArrayList

@Service
class ShiftService(
    @Autowired
    val shiftHopeRepository: ShiftHopeRepository
) {
    fun getShift(employeeId: String, year: Int, month: Int): ShiftHopeResponse {
        val shifts = shiftHopeRepository.findByEmployeeId(employeeId)
        val days = ArrayList<Int>()
        shifts.forEach {
            val calendar = Calendar.getInstance()
            calendar.timeInMillis = it.date.time
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
            println("$year$month$day")
            val date = try {
                val dateFormat: DateFormat = SimpleDateFormat("yyyyMMdd")
                dateFormat.isLenient = false
                dateFormat.parse("$year$month$day")
            } catch (e: ParseException) {
                return false
            }

            println(date)
            val shiftHope = ShiftHope(
                Date(date.time),
                employeeId
            )
            shiftHopeRepository.save(shiftHope)
        }
        return true
    }
}