package com.rinats.rin.controller.storemanager

import com.rinats.rin.service.ShiftService
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import java.util.*

@Controller
@RequestMapping("/shift")
class ShiftController(
    private val shiftService: ShiftService
) {
    @GetMapping("")
    fun index(
        model: Model,
        @RequestParam(name = "year", required = false) _year: Int?,
        @RequestParam(name = "month", required = false) _month: Int?
    ): String {
        if (_year != null && _month != null) {
            model.addAttribute("shifts", shiftService.getAllShift(_year, _month))
        } else {
            val calendar = Calendar.getInstance()
            val year = calendar.get(Calendar.YEAR)
            val month = calendar.get(Calendar.MONTH) + 1
            model.addAttribute("shifts", shiftService.getAllShift(year, month))
        }
        return "ThisMonthShiftCheck"
    }
}