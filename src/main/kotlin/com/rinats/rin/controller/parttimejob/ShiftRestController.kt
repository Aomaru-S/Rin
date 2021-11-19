package com.rinats.rin.controller.parttimejob

import com.rinats.rin.model.Employee
import com.rinats.rin.model.form.ShiftHopeForm
import com.rinats.rin.service.ShiftService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.validation.BindingResult
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestAttribute
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import javax.servlet.http.HttpServletRequest

@RestController
@RequestMapping("api/v1/shift")
class ShiftRestController(
    @Autowired
    val shiftService: ShiftService,
) {

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
        shiftService.submit(shiftHopeForm, employee.employeeId)
    }
}