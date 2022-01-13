package com.rinats.rin.controller.parttimejob

import com.rinats.rin.annotation.NonAuth
import com.rinats.rin.model.Employee
import com.rinats.rin.model.form.GetShiftsForm
import com.rinats.rin.model.response.Shift
import com.rinats.rin.model.response.ShiftResponse
import com.rinats.rin.service.ShiftHopeService
import com.rinats.rin.service.ShiftService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.validation.BindingResult
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("api/shift")
class ShiftRestController(
    @Autowired
    val shiftService: ShiftService,
) {
    @GetMapping("/shift_table_check")
    fun checkAllShift(
        @Validated getShiftsForm: GetShiftsForm,
        bindingResult: BindingResult
    ): ShiftResponse? {
        if (bindingResult.hasErrors()) {
            return null
        }
        getShiftsForm.year ?: return null
        getShiftsForm.month ?: return null
        return shiftService.getAllShift(getShiftsForm.year, getShiftsForm.month)
    }

    @GetMapping("/shift_check")
    fun checkShift(
        @RequestHeader("Authorization") token: String,
        @Validated getShiftsForm: GetShiftsForm,
        bindingResult: BindingResult
    ): Shift? {
        if (bindingResult.hasErrors()) {
            return null
        }
        getShiftsForm.year ?: return null
        getShiftsForm.month ?: return null
        return shiftService.getShift(getShiftsForm.year, getShiftsForm.month, token)
    }
}