package com.rinats.rin.controller.storemanager

import com.rinats.rin.model.other.PrevNextYearMonth
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
        @RequestParam(name = "year") _year: Int? = null,
        @RequestParam(name = "month") _month: Int? = null,
    ): String {
        var year: Int
        var month: Int
        Calendar.getInstance().let {
            year = _year ?: it.get(Calendar.YEAR)
            month = _month ?: (it.get(Calendar.MONTH) + 1)
        }

        val calendar = Calendar.getInstance().also {
            it.isLenient = false
            it.set(Calendar.YEAR, year)
            it.set(Calendar.MONTH, month - 1)
        }
        val dayCount = calendar.getActualMaximum(Calendar.DAY_OF_MONTH)

        val shiftResponse = shiftService.getAllShift(year, month)
        val map = mutableMapOf<String, MutableList<Int>>()
        shiftResponse?.days?.forEach {
            if (map.containsKey(it.employeeId)) {
                map[it.employeeId]?.add(
                    it.day
                )
            } else {
                map[it.employeeId] = mutableListOf(it.day)
            }
        }

        val prevNextYearMonth = PrevNextYearMonth(
            if (month == 1) year - 1 else year,
            if (month == 1) 12 else month - 1,
            if (month == 12) year + 1 else year,
            if (month == 12) 1 else month + 1
        )

        model.addAttribute("prevNext", prevNextYearMonth)
        model.addAttribute("map", map)
        model.addAttribute("year", year)
        model.addAttribute("month", month)
        model.addAttribute("dayCount", dayCount)
        return "ThisMonthShiftCheck"
    }
}