package com.rinats.rin.controller

import com.rinats.rin.annotation.NonAuth
import com.rinats.rin.model.form.TableRegistrationForm
import com.rinats.rin.service.TableRegistrationService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Controller
import org.springframework.validation.BindingResult
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.ModelAttribute
import org.springframework.web.bind.annotation.PostMapping

@Controller
class TableRegistrationController(
    @Autowired
    val tableRegistrationService: TableRegistrationService
) {
    @NonAuth
//    @PostMapping("/table_registration")
    fun tableRegistration(
        @Validated
        @ModelAttribute
        tableRegistrationForm: TableRegistrationForm,
        validationResult: BindingResult
    ) {
        tableRegistrationService.tableRegistration(tableRegistrationForm.name ?: "", tableRegistrationForm.numOfPeople ?: 0)
    }
}