package com.rinats.rin.controller.storemanager

import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.GetMapping

@Controller
class ShiftPlanController {

    @GetMapping("/shift_plan_setting")
    fun shiftPlanSetting(): String {
        return "shift_plan_setting"
    }

    @GetMapping("/shift_template")
    fun shiftTemplate(): String {
        return "shift_template"
    }

    @GetMapping("/template_setting")
    fun templateSetting(): String {
        return "TemplateConfiguration"
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