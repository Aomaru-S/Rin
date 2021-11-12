package com.rinats.rin.controller

import com.rinats.rin.model.Employee
import com.rinats.rin.model.Role
import com.rinats.rin.model.form.AuthForm
import com.rinats.rin.repository.EmployeeRepository
import com.rinats.rin.repository.RoleRepository
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController


@Controller
class TestController {

    @GetMapping("/form")
    fun form(model: Model): String {
        model.addAttribute("authForm", AuthForm())
        return "TestForm"
    }
}