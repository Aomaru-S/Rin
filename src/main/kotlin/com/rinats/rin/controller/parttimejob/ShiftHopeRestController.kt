package com.rinats.rin.controller.parttimejob

import com.rinats.rin.model.Employee
import com.rinats.rin.model.form.ShiftHopeForm
import com.rinats.rin.model.response.ShiftHopeResponse
import com.rinats.rin.service.ShiftHopeService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.validation.BindingResult
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/shift_hope")
class ShiftHopeRestController(
    @Autowired
    private val shiftHopeService: ShiftHopeService
) {

    /*@GetMapping("shift_hope")
    fun getShiftHope(
        @RequestAttribute
        employee: Employee,
        getShiftHopeForm: ShiftHopeForm,
        bindingResult: BindingResult
    ): ShiftHopeResponse {
        if (bindingResult.hasErrors()) {

        }
    }*/

    @PostMapping("shift_hope")
    fun postShiftHope(
        @RequestAttribute
        employee: Employee,
        @ModelAttribute
        shiftHopeForm: ShiftHopeForm,
        bindingResult: BindingResult
    ): HashMap<String, Boolean> {
        if (bindingResult.hasErrors()) {
            return hashMapOf("result" to false)
        }
        val result = shiftHopeService.submitShift(shiftHopeForm, employee.employeeId)
        return hashMapOf("result" to result)
    }
}