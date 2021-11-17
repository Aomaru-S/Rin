package com.rinats.rin.controller

import com.rinats.rin.annotation.NonAuth
import com.rinats.rin.model.form.TableRegistrationForm
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
        tableRegistrationForm: TableRegistrationForm,
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
    @PostMapping("table_edit")
    fun tableEdit(request: HttpServletRequest, model: Model): String {
        val name = request.getParameter("name")
        val numOfPeople = request.getParameter("numOfPeople")
        model.addAttribute("name", name)
        model.addAttribute("numOfPeople", numOfPeople)
        return "TableEditPage"
    }

    @NonAuth
    @PostMapping("/table_edit_conf")
    fun tableEditConf(request: HttpServletRequest, model: Model): String {
        val name = request.getParameter("name")
        val numOfPeople = request.getParameter("numOfPeople")
        model.addAttribute("name", name)
        model.addAttribute("numOfPeople", numOfPeople)
        return "TableEditConfPage"
    }

    @NonAuth
    @PostMapping("/table_edit_complete")
    fun tableEditComplete(request: HttpServletRequest, model: Model,): String {
        val name = request.getParameter("name")
        val numOfPeople = Integer.parseInt(request.getParameter("numOfPeople"))
        println("################")
        tableService.tableUpdate(name, numOfPeople)
        return "TableEditCompletePage"
    }

}