package com.rinats.rin.controller.storemanager

import com.rinats.rin.model.form.ShiftTemplateForm
import com.rinats.rin.model.form.ShiftTemplateFormList
import com.rinats.rin.model.other.CompleteMessage
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
    fun editShiftTemplateForm(
        model: Model
    ): String {
        val shiftTemplateForm = ShiftTemplateForm(
            1, 1, 1, 1, 1, 1, 1, 1, 1
        )
        val shiftTemplateForm2 = ShiftTemplateForm(
            2, 2, 2, 2, 2, 2, 2, 2, 2
        )
        val list = listOf(shiftTemplateForm, shiftTemplateForm2)
        val form = ShiftTemplateFormList()
        form.shiftTemplateList = list
        model.addAttribute("shiftTemplateFormList", form)
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
        println("get_edit_confirm: $shiftTemplateFormList")
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
        println("post_edit: $shiftTemplateFormList")
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