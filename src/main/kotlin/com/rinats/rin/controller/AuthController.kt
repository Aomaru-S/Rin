package com.rinats.rin.controller

import com.rinats.rin.model.form.AuthForm
import com.rinats.rin.service.AuthService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.validation.BindingResult
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.ModelAttribute
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RestController
import java.util.*

@RestController
class AuthController(
    @Autowired
    val authService: AuthService
) {
    @PostMapping("/login")
    fun login(
        @Validated
        @ModelAttribute
        authForm: AuthForm,
        validationResult: BindingResult
    ): UUID? {
        val accessToken = authService.loginWithGetAccessToken(authForm.employeeId ?: "", authForm.password ?: "")
        if (validationResult.hasErrors() || accessToken == null) {
            return null
        }
        return accessToken
    }
}