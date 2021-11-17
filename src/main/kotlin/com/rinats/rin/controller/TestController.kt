package com.rinats.rin.controller

import com.rinats.rin.annotation.NonAuth
import com.rinats.rin.model.form.AddEmployeeForm
import com.rinats.rin.model.form.AuthForm
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.GetMapping


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