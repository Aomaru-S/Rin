package com.rinats.rin.controller

import com.rinats.rin.model.form.TableRegistrationForm
import com.rinats.rin.service.TableRegistrationService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.validation.BindingResult
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.ModelAttribute
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class TableRegistrationController(
    @Autowired
    val tableRegistrationService: TableRegistrationService
) {
    @PostMapping("/table_registration")
    fun tableRegistration(
        @Validated
        @ModelAttribute
        tableRegistrationForm: TableRegistrationForm,
        validationResult: BindingResult
    ) {
        tableRegistrationService.tableRegistration(tableRegistrationForm.name ?: "", tableRegistrationForm.numOfPeople ?: 0)
    }
}