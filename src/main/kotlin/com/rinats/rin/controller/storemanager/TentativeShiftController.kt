package com.rinats.rin.controller.storemanager

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.rinats.rin.model.other.CompleteMessage
import com.rinats.rin.model.table.Employee
import com.rinats.rin.service.ShiftGeneratorService
import com.rinats.rin.service.TentativeShiftService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import java.util.*

@Controller
@RequestMapping("/tentative_shift")
class TentativeShiftController(
    @Autowired
    private val tentativeShiftService: TentativeShiftService,
    private val shiftGeneratorService: ShiftGeneratorService
) {
    @GetMapping("")
    fun index(
        model: Model
    ): String {
        val calendar = Calendar.getInstance()
        calendar.isLenient = false
        calendar.add(Calendar.MONTH, 1)
        val dayCount = calendar.getActualMaximum(Calendar.DAY_OF_MONTH)
        val year = calendar.get(Calendar.YEAR)
        val month = calendar.get(Calendar.MONTH) + 1
        calendar.set(Calendar.DAY_OF_MONTH, 1)
        var firstDayOfWeek = calendar.get(Calendar.DAY_OF_WEEK)
        val dayOfWeekMap = mutableMapOf<Int, String>()
        for (i in 1..7) {
            dayOfWeekMap[i] = when (firstDayOfWeek) {
                1 -> "土"
                2 -> "日"
                3 -> "月"
                4 -> "火"
                5 -> "水"
                6 -> "木"
                7 -> "金"
                else -> ""
            }
            firstDayOfWeek++
            if (firstDayOfWeek == 8) {
                firstDayOfWeek -= 7
            }
        }

        val tentativeShiftMap = mutableMapOf<Employee, MutableList<Int>>()
        tentativeShiftService.getTentativeShiftList().forEach {
            if (tentativeShiftMap.containsKey(it.id?.employee)) {
                tentativeShiftMap[it.id?.employee]?.add(it.id?.shiftDate!!.dayOfMonth)
            } else {
                tentativeShiftMap[it.id?.employee!!] = mutableListOf(it.id?.shiftDate!!.dayOfMonth)
            }
        }
        model.addAttribute("year", year)
        model.addAttribute("month", month)
        model.addAttribute("dayOfWeekMap", dayOfWeekMap)
        model.addAttribute("tentativeShiftList", tentativeShiftMap)
        model.addAttribute("dayCount", dayCount)
        return "tentative_shift_index"
    }

    @GetMapping("/edit")
    fun editTentativeShift(model: Model): String {
        val calendar = Calendar.getInstance()
        calendar.isLenient = false
        calendar.add(Calendar.MONTH, 1)
        val dayCount = calendar.getActualMaximum(Calendar.DAY_OF_MONTH)
        val year = calendar.get(Calendar.YEAR)
        val month = calendar.get(Calendar.MONTH) + 1
        calendar.set(Calendar.DAY_OF_MONTH, 1)
        var firstDayOfWeek = calendar.get(Calendar.DAY_OF_WEEK)
        val dayOfWeekMap = mutableMapOf<Int, String>()
        for (i in 1..7) {
            dayOfWeekMap[i] = when (firstDayOfWeek) {
                1 -> "土"
                2 -> "日"
                3 -> "月"
                4 -> "火"
                5 -> "水"
                6 -> "木"
                7 -> "金"
                else -> ""
            }
            firstDayOfWeek++
            if (firstDayOfWeek == 8) {
                firstDayOfWeek -= 7
            }
        }

        val tentativeShiftMap = mutableMapOf<Employee, MutableList<Int>>()
        tentativeShiftService.getTentativeShiftList().forEach {
            if (tentativeShiftMap.containsKey(it.id?.employee)) {
                tentativeShiftMap[it.id?.employee]?.add(it.id?.shiftDate!!.dayOfMonth)
            } else {
                tentativeShiftMap[it.id?.employee!!] = mutableListOf(it.id?.shiftDate!!.dayOfMonth)
            }
        }
        model.addAttribute("year", year)
        model.addAttribute("month", month)
        model.addAttribute("dayOfWeekMap", dayOfWeekMap)
        model.addAttribute("tentativeShiftList", tentativeShiftMap)
        model.addAttribute("dayCount", dayCount)

        return "edit_tentative_shift"
    }

    @PostMapping("/edit")
    fun editTentativeShiftConfirm(
        model: Model,
        @RequestParam(required = false) changeAttendanceJson: String? = null
    ): String {
        val mapType = object : TypeToken<MutableMap<String, MutableList<AttendanceDay>>>() {}.type
        val changeAttendance =
            Gson().fromJson<MutableMap<String, MutableList<AttendanceDay>>>(changeAttendanceJson, mapType)
        tentativeShiftService.editAttendance(changeAttendance)
        return "redirect:/tentative_shift"
    }

    @GetMapping("/submit")
    fun confirmTentativeShiftSubmit(
        model: Model
    ): String {
        val tentativeShiftList = tentativeShiftService.getTentativeShiftList()
        model.addAttribute("tentativeShiftList", tentativeShiftList)
        return "confirm_tentative_shift_submit"
    }

    @PostMapping("/submit")
    fun submitTentativeShift(
        model: Model
    ): String {
        tentativeShiftService.submitTentativeShift()
        val message = CompleteMessage("シフト確定: Rin", "シフトが公開されました。")
        model.addAttribute("message", message)
        tentativeShiftService.getTentativeShiftList()
        return "complete"
    }

    data class AttendanceDay(
        val day: String,
        val isAttendance: Boolean
    )
}