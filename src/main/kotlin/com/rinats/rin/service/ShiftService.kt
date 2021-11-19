package com.rinats.rin.service

import com.rinats.rin.model.ShiftHope
import com.rinats.rin.model.form.ShiftHopeForm
import com.rinats.rin.repository.ShiftHopeRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import java.text.DateFormat
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.Calendar
import java.sql.Date

@Service
class ShiftService(
    @Autowired
    val shiftHopeRepository: ShiftHopeRepository
) {
    fun submit(shiftHopeForm: ShiftHopeForm, employeeId: String): Boolean {
        val calendar = Calendar.getInstance()
        calendar.timeInMillis = 0
        val today = Calendar.getInstance()
        val dateFormat: DateFormat = SimpleDateFormat("yyyyMMdd")
        dateFormat.isLenient = false

        val year = today.get(Calendar.YEAR).toString().padStart(4, '0')
        val month = (today.get(Calendar.MONTH) + 1).toString().padStart(2, '0')
        val day = shiftHopeForm.day.toString().padStart(2, '0')

        val date = try {
            dateFormat.parse("$year$month$day")
        } catch (e: ParseException) {
            return false
        }

        val shiftHope = ShiftHope(
            Date(date.time),
            employeeId
        )
        shiftHopeRepository.save(shiftHope)
        return true
    }
}