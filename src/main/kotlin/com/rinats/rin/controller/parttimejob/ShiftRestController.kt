package com.rinats.rin.controller.parttimejob

import com.rinats.rin.model.Employee
import com.rinats.rin.model.form.GetShiftsForm
import com.rinats.rin.model.form.ShiftHopeForm
import com.rinats.rin.model.response.ShiftHopeResponse
import com.rinats.rin.service.ShiftService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.validation.BindingResult
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.*
import java.util.*
import javax.servlet.http.HttpServletRequest
import kotlin.collections.HashMap

@RestController
@RequestMapping("api/v1/shift")
class ShiftRestController(
    @Autowired
    val shiftService: ShiftService,
) {

    @GetMapping("/shift_request")
    fun getShiftHope(
        @RequestAttribute employee: Employee,
        @Validated getShiftsForm: GetShiftsForm,
        bindingResult: BindingResult
    ): ShiftHopeResponse? {
        if (bindingResult.hasErrors()) {
            return null
        }
        return shiftService.getShift(
            employee.employeeId,
            getShiftsForm.year,
            getShiftsForm.month
        )
    }

    @PostMapping("/shift_request")
    fun submitShiftHope(
        @RequestAttribute employee: Employee,
        @Validated shiftHopeForm: ShiftHopeForm,
        bindingResult: BindingResult
    ): HashMap<String, Boolean> {
        if (bindingResult.hasErrors()) {
            return hashMapOf("result" to false)
        }
        val result = shiftService.submitShift(shiftHopeForm, employee.employeeId)
        return hashMapOf("result" to result)
    }

    @GetMapping("/shift_table_check")
    fun checkShiftTable(
        @RequestAttribute employee: Employee,
        @Validated getShiftsForm: GetShiftsForm,
        bindingResult: BindingResult
    ) {

    }
}