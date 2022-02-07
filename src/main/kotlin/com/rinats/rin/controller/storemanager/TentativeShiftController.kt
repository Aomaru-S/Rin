package com.rinats.rin.controller.storemanager

import com.rinats.rin.model.other.CompleteMessage
import com.rinats.rin.service.TentativeShiftService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import java.util.*

@Controller
@RequestMapping("/tentative_shift")
class TentativeShiftController(
    @Autowired
    private val tentativeShiftService: TentativeShiftService
) {
    @GetMapping("")
    fun index(
        model: Model
    ): String {
        val tentativeShiftList = tentativeShiftService.getTentativeShiftList()
        val year = tentativeShiftList[0].id?.shiftDate?.year
        val month = tentativeShiftList[0].id?.shiftDate?.month?.value
        val calendar = Calendar.getInstance()
        calendar.isLenient = false
        if (year != null && month != null) {
            calendar.set(year, month - 1, 1)
        }
        val beforeBlank = 7 - calendar.get(Calendar.DAY_OF_WEEK)
        model.addAttribute("beforeBlank", beforeBlank)
        val lastDay = calendar.getActualMaximum(Calendar.DAY_OF_MONTH)
        calendar.set(Calendar.DAY_OF_MONTH, lastDay)
        val afterBlank = 7 - calendar.get(Calendar.DAY_OF_WEEK)
        model.addAttribute("afterBlank", afterBlank)
        model.addAttribute("tentativeShiftList", tentativeShiftList)
        return "tentative_shift_index"
    }

    @GetMapping("/edit")
    fun editTentativeShift(): String {
        return "edit_tentative_shift"
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
}