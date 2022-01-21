package com.rinats.rin.controller.storemanager

import com.rinats.rin.model.other.CompleteMessage
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping

@Controller
@RequestMapping("/attendance_departure")
class AttendanceDepartureController {
    @GetMapping("")
    fun index(): String {
        return "AttendanceCheck"
    }

    @GetMapping("/edit")
    fun editAttendanceDepartureForm(): String {
        return "AttendanceEditing"
    }

    @PostMapping("/edit_confirm")
    fun editAttendanceDepartureConfirm(): String {
        return "AttendanceEditingCheck"
    }

    @PostMapping("/edit")
    fun editAttendanceDeparture(
        model: Model
    ): String {
        val message = CompleteMessage("出退勤編集完了: Rin", "出退勤履歴の編集が完了しました。")
        model.addAttribute("message", message)
        return "complete"
    }

    @GetMapping("/edit_history")
    fun editHistory(): String {
        return "AttendanceEditHistory"
    }
}
