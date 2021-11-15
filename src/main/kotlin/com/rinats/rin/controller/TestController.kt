package com.rinats.rin.controller

import com.rinats.rin.annotation.NonAuth
import com.rinats.rin.model.Employee
import com.rinats.rin.model.Role
import com.rinats.rin.model.form.AddEmployeeForm
import com.rinats.rin.model.form.AuthForm
import com.rinats.rin.repository.EmployeeRepository
import com.rinats.rin.repository.RoleRepository
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController


@Controller
class TestController {
    @NonAuth
    @GetMapping("/")
    fun form(model: Model): String {
        model.addAttribute("addEmployeeForm", AddEmployeeForm())
        model.addAttribute("authForm", AuthForm())
        return "TestForm"
    }
}