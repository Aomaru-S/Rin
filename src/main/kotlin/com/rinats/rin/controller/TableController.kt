package com.rinats.rin.controller

import com.rinats.rin.model.form.TableForm
import com.rinats.rin.service.TableService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.validation.BindingResult
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.ModelAttribute
import org.springframework.web.bind.annotation.PostMapping

@Controller
class TableController(
    @Autowired
    val tableService: TableService
) {

    @GetMapping("table_registration")
    fun tableRegistration(
        @Validated
        @ModelAttribute
        tableRegistrationForm: TableForm,
        validationResult: BindingResult
    ): String {
        return "TableRegistration"
    }

    @PostMapping("/table_registration_complete")
    fun tableRegistrationComplete(
        @Validated
        @ModelAttribute
        tableForm: TableForm,
        validationResult: BindingResult
    ): String {
        tableService.tableUpdate(tableForm.name ?: "", tableForm.numOfPeople ?: 0)
        return "redirect:table_check"
    }

    @GetMapping("/table_check")
    fun tableCheck(model: Model): String {
        model.addAttribute("tableList", tableService.getTable())
        return "TableCheck"
    }

    @PostMapping("/table_edit")
    fun tableEdit(model: Model, tableForm: TableForm): String {
        model.addAttribute("name", tableForm.name)
        model.addAttribute("numOfPeople", tableForm.numOfPeople)
        return "TableEdit"
    }

    @PostMapping("/table_edit_conf")
    fun tableEditConf(model: Model, tableForm: TableForm): String {
        model.addAttribute("name", tableForm.name)
        model.addAttribute("numOfPeople", tableForm.numOfPeople)
        return "TableEditConf"
    }

    @PostMapping("/table_edit_complete")
    fun tableEditComplete(tableForm: TableForm): String {
        tableService.tableUpdate(tableForm.name ?: "", tableForm.numOfPeople ?: 0)
        return "TableEditComplete"
    }

    @PostMapping("/table_delete")
    fun tableDelete(tableForm: TableForm): String {
        tableService.tableDelete(tableForm.name ?: "", tableForm.numOfPeople ?: 0)
        return "redirect:table_check"
    }

}