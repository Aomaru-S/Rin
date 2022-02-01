package com.rinats.rin.controller.storemanager

import com.rinats.rin.model.form.ShiftTemplateFormList
import com.rinats.rin.model.other.CompleteMessage
import com.rinats.rin.service.ShiftTemplateService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.validation.BindingResult
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.ModelAttribute
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping

@Controller
@RequestMapping("/shift_plan")
class ShiftPlanController(
    @Autowired
    private val shiftTemplateService: ShiftTemplateService
) {

    @GetMapping("")
    fun index(): String {
        return "shift_plan_setting"
    }

    @GetMapping("/template")
    fun getTemplate(
        model: Model
    ): String {
        model.addAttribute("shiftTemplateFormList", shiftTemplateService.getShiftTemplate())
        return "shift_template"
    }

    @GetMapping("/template/edit")
    fun editShiftTemplateForm(
        model: Model
    ): String {
        model.addAttribute("shiftTemplateFormList", ShiftTemplateFormList().also {
            it.shiftTemplateList = shiftTemplateService.getShiftTemplate() ?: return "redirect:/template/edit?error"
        })
        return "TemplateConfiguration"
    }

    @PostMapping("/template/edit_confirm")
    fun editShiftTemplateConfirm(
        @Validated
        @ModelAttribute
        shiftTemplateFormList: ShiftTemplateFormList,
        bindingResult: BindingResult,
        model: Model
    ): String {
        if (bindingResult.hasErrors()) {
            return "redirect:/edit?validation_error"
        }
        model.addAttribute("shiftTemplateFormList", shiftTemplateFormList)
        return "edit_template_confirm"
    }

    @PostMapping("/template/edit")
    fun editShiftTemplate(
        @Validated
        @ModelAttribute
        shiftTemplateFormList: ShiftTemplateFormList,
        bindingResult: BindingResult,
        model: Model
    ): String {
        shiftTemplateService.saveShiftTemplate(shiftTemplateFormList)
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