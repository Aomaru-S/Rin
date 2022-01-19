package com.rinats.rin.controller.parttimejob

import com.rinats.rin.model.table.Employee
import com.rinats.rin.model.form.GetShiftHopeForm
import com.rinats.rin.model.form.ShiftHopeForm
import com.rinats.rin.model.response.ShiftHopeResponse
import com.rinats.rin.service.ShiftHopeService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.validation.BindingResult
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/shift_hope")
class ShiftHopeRestController(
    @Autowired
    private val shiftHopeService: ShiftHopeService
) {

    @GetMapping("shift_hope")
    fun getShiftHope(
        @RequestAttribute
        employee: Employee,
        getShiftHopeForm: GetShiftHopeForm,
        bindingResult: BindingResult
    ): ShiftHopeResponse? {
        if (bindingResult.hasErrors()) {
            return null
        }
        return shiftHopeService.getShiftHope(
            employee.id ?: return null,
            getShiftHopeForm.year ?: return null,
            getShiftHopeForm.month ?: return null
        )
    }

    @PostMapping("shift_hope")
    fun postShiftHope(
        @RequestAttribute
        employee: Employee,
        @Validated
        @ModelAttribute
        shiftHopeForm: ShiftHopeForm,
        bindingResult: BindingResult
    ): HashMap<String, Boolean> {
        if (bindingResult.hasErrors()) {
            System.err.println("postShiftHope")
            return hashMapOf("result" to false)
        }
        System.err.println("postShiftHope2")
        val result = shiftHopeService.submitShift(shiftHopeForm, employee.id ?: return hashMapOf("result" to false))
        return hashMapOf("result" to result)
    }

    @DeleteMapping("shift_hope")
    fun deleteShiftHope(
        @RequestAttribute
        employee: Employee
    ) {
        val id = employee.id ?: return
        shiftHopeService.deleteShiftHope(id)
    }
}