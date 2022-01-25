package com.rinats.rin.controller.storemanager

import com.rinats.rin.model.other.CompleteMessage
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping

@Controller
@RequestMapping("/shift_plan")
class ShiftPlanController {

    @GetMapping("")
    fun index(): String {
        return "shift_plan_setting"
    }

    @GetMapping("/template")
    fun getTemplate(
        model: Model
    ): String {
        return "shift_template"
    }

    @GetMapping("/template/edit")
    fun editShiftTemplateForm(): String {
        return "TemplateConfiguration"
    }

    @GetMapping("/template/edit_confirm")
    fun editShiftTemplateConfirm(): String {
        return "edit_template_confirm"
    }

    @PostMapping("/template/edit")
    fun editShiftTemplate(
        model: Model
    ): String {
        val message = CompleteMessage("テンプレート変更完了: Rin", "シフトテンプレートが編集されました")
        model.addAttribute("message", message)
        return "complete"
    }

    @GetMapping("/add_edit_shift")
    fun addEditShift(): String {
        return "add_edit_shift"
    }

    @GetMapping("add_edit_shift_confirm")
    fun addEditShiftConfirm(): String {
        return "add_edit_shift_confirm"
    }
}