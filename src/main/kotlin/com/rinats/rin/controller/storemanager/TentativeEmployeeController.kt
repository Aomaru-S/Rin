package com.rinats.rin.controller.storemanager

import com.rinats.rin.model.form.AddTentativeEmployeeForm
import com.rinats.rin.service.TentativeEmployeeService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.validation.BindingResult
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.ModelAttribute
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class TentativeEmployeeController(
    @Autowired
    private val tentativeEmployeeService: TentativeEmployeeService
) {

    @PostMapping("/add_tentative_employee")
    fun addTentativeEmployee(
        @Validated
        @ModelAttribute
        addTentativeEmployeeForm: AddTentativeEmployeeForm,
        bindingResult: BindingResult
    ) {
        if (bindingResult.hasErrors()) {
            return
        }
        tentativeEmployeeService.addTentativeEmployee(addTentativeEmployeeForm)
    }
}