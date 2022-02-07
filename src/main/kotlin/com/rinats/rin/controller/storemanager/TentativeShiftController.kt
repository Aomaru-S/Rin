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
        val calendar = Calendar.getInstance()
        val dayCount = calendar.getActualMaximum(Calendar.DAY_OF_MONTH)
        val tentativeShiftList = mutableMapOf<String, MutableList<Int>>()
        tentativeShiftService.getTentativeShiftList().forEach {
            if (tentativeShiftList.containsKey(it.id?.employee?.id ?: throw IllegalStateException())) {
                tentativeShiftList[it.id?.employee?.id ?: throw IllegalStateException()]?.add(
                    it.id?.shiftDate?.dayOfMonth ?: throw IllegalStateException()
                )
            } else {
                tentativeShiftList[it.id?.employee?.id ?: throw IllegalStateException()] =
                    mutableListOf(it.id?.shiftDate?.dayOfMonth ?: throw IllegalStateException())
            }
        }
        model.addAttribute("tentativeShiftList", tentativeShiftList)
        model.addAttribute("dayCount", dayCount)
        return "tentative_shift_index"
    }

    @GetMapping("/edit")
    fun editTentativeShift(model: Model): String {
        val calendar = Calendar.getInstance()
        val dayCount = calendar.getActualMaximum(Calendar.DAY_OF_MONTH)
        val tentativeShiftList = mutableMapOf<String, MutableList<Int>>()
        tentativeShiftService.getTentativeShiftList().forEach {
            if (tentativeShiftList.containsKey(it.id?.employee?.id ?: throw IllegalStateException())) {
                tentativeShiftList[it.id?.employee?.id ?: throw IllegalStateException()]?.add(
                    it.id?.shiftDate?.dayOfMonth ?: throw IllegalStateException()
                )
            } else {
                tentativeShiftList[it.id?.employee?.id ?: throw IllegalStateException()] =
                    mutableListOf(it.id?.shiftDate?.dayOfMonth ?: throw IllegalStateException())
            }
        }
        if (tentativeShiftList.isEmpty()) {
            return "redirect:/tentative_shift"
        }
        model.addAttribute("tentativeShiftList", tentativeShiftList)
        model.addAttribute("dayCount", dayCount)
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