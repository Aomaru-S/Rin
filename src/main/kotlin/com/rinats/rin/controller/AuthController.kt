package com.rinats.rin.controller

import com.rinats.rin.annotation.NonAuth
import com.rinats.rin.model.form.AuthForm
import com.rinats.rin.service.AuthService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.validation.BindingResult
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.*

@RestController
class AuthController(
    @Autowired
    val authService: AuthService
) {
    @NonAuth
    @PostMapping("/login")
    fun login(
        @Validated
        @ModelAttribute
        authForm: AuthForm,
        validationResult: BindingResult
    ): String? {
        println("auth")
        val accessToken = authService.loginWithGetAccessToken(authForm.employeeId ?: "", authForm.password ?: "")
        if (validationResult.hasErrors() || accessToken == null) {
            println("true")
            return null
        }
        println("false")
        return accessToken
    }

    @NonAuth
    @PostMapping("/check_access_token")
    fun checkAccessToken(
        @RequestParam("access_token")
        accessToken: String
    ): Boolean {
        return authService.checkAccessToken(accessToken)
    }

    @PostMapping("logout")
    fun logout(
        @RequestParam("access_token")
        accessToken: String
    ) {
        authService.logout(accessToken)
    }
}