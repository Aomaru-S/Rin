package com.rinats.rin.controller.storemanager

import com.rinats.rin.model.other.CompleteMessage
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping

@Controller
@RequestMapping("/tentative_shift")
class TentativeShiftController {
    @GetMapping("/")
    fun index(): String {
        return "tentative_shift_index"
    }

    @GetMapping("/edit")
    fun editTentativeShift(): String {
        return "edit_tentative_shift"
    }

    @GetMapping("/submit")
    fun confirmTentativeShiftSubmit(): String {
        return "confirm_tentative_shift_submit"
    }

    @PostMapping("/submit")
    fun submitTentativeShift(
        model: Model
    ): String {
        val message = CompleteMessage("シフト確定: Rin", "シフトが公開されました。")
        model.addAttribute("message", message)
        return "complete"
    }
}