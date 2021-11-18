package com.rinats.rin.controller.parttimejob

import com.rinats.rin.model.Employee
import com.rinats.rin.model.ShiftHope
import com.rinats.rin.repository.ShiftHopeRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import java.util.*
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpSession

@RestController
@RequestMapping("api/v1/shift")
class ShiftRestController(
    @Autowired
    val shiftHopeRepository: ShiftHopeRepository,
    val session: HttpSession
) {
    @GetMapping("/submit")
    fun submitShift(request: HttpServletRequest): String {
        val employee = request.getAttribute("employee") as Employee

        val today = Calendar.getInstance()
        val year = today.get(Calendar.YEAR)
        val month = today.get(Calendar.MONTH)
        val day = today.get(Calendar.DAY_OF_MONTH)

        val calendar = Calendar.getInstance()
        calendar.timeInMillis = 0

        val shiftHope = ShiftHope(
            java.sql.Date(calendar.time.time),
            employee.employeeId
        )
        shiftHopeRepository.save(shiftHope)
        return "Hello"
    }
}