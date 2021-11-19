package com.rinats.rin.controller

import com.rinats.rin.annotation.NonAuth
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
import javax.servlet.http.HttpServletRequest

@Controller
class TableController(
    @Autowired
    val tableService: TableService
) {

    @NonAuth
    @PostMapping("/table_registration")
    fun tableRegistration(
        @Validated
        @ModelAttribute
        tableRegistrationForm: TableForm,
        validationResult: BindingResult
    ) {
        tableService.tableRegistration(tableRegistrationForm.name ?: "", tableRegistrationForm.numOfPeople ?: 0)
    }

    @NonAuth
    @GetMapping("/table_check")
    fun tableCheck(model: Model): String {
        model.addAttribute("tableList", tableService.getTable())
        return "TableCheckPage"
    }

    @NonAuth
    @PostMapping("/table_edit")
    fun tableEdit(model: Model, tableForm: TableForm): String {
        model.addAttribute("name", tableForm.name)
        model.addAttribute("numOfPeople", tableForm.numOfPeople)
        return "TableEditPage"
    }

    @NonAuth
    @PostMapping("/table_edit_conf")
    fun tableEditConf(model: Model, tableForm: TableForm): String {
        model.addAttribute("name", tableForm.name)
        model.addAttribute("numOfPeople", tableForm.numOfPeople)
        return "TableEditConfPage"
    }

    @NonAuth
    @PostMapping("/table_edit_complete")
    fun tableEditComplete(tableForm: TableForm): String {
        tableService.tableUpdate(tableForm.name ?: "", tableForm.numOfPeople ?: 0)
        return "TableEditCompletePage"
    }

}