package com.rinats.rin.controller.parttimejob

import com.rinats.rin.annotation.NonAuth
import com.rinats.rin.model.Employee
import com.rinats.rin.model.ShiftHope
import com.rinats.rin.model.form.ShiftHopeForm
import com.rinats.rin.repository.ShiftHopeRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.validation.BindingResult
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestAttribute
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import java.util.Calendar
import java.sql.Date
import javax.servlet.http.HttpServletRequest

@RestController
@RequestMapping("api/v1/shift")
class ShiftRestController(
    @Autowired
    val shiftHopeRepository: ShiftHopeRepository,
) {

//    @NonAuth
    @GetMapping("/submit")
    fun submitShift(
        request: HttpServletRequest,
        @RequestAttribute employee: Employee,
        @Validated shiftHopeForm: ShiftHopeForm,
        bindingResult: BindingResult
    ) {
        if (bindingResult.hasErrors()) {
            return
        }

        val today = Calendar.getInstance()

        val calendar = Calendar.getInstance()
        calendar.timeInMillis = 0
        calendar.set(Calendar.YEAR, today.get(Calendar.YEAR))
        calendar.set(Calendar.MONTH, today.get(Calendar.MONTH))
        calendar.set(Calendar.DAY_OF_MONTH, today.get(Calendar.DAY_OF_MONTH))

//        val shiftHope = ShiftHope(
//            Date(calendar.time.time),
//            employee.employeeId
//        )
//        shiftHopeRepository.save(shiftHope)
        println("Calendar: ${calendar.time.time}")
        println("Calendar: ${Date(calendar.time.time)}")
        println(employee.employeeId)
    }
}