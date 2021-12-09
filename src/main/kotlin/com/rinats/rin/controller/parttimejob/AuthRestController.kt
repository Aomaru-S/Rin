package com.rinats.rin.controller.parttimejob

import com.rinats.rin.annotation.NonAuth
import com.rinats.rin.model.form.AuthForm
import com.rinats.rin.service.AuthService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.validation.BindingResult
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("api/auth")
@CrossOrigin(methods = [RequestMethod.POST, RequestMethod.OPTIONS])
class AuthRestController(
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
    ): HashMap<String, String?> {
        val accessToken = authService.login(authForm.employeeId ?: "", authForm.password ?: "")
        if (validationResult.hasErrors() || accessToken == null) {
            return hashMapOf("access_token" to null)
        }
        return hashMapOf("access_token" to accessToken)
    }


    @NonAuth
    @GetMapping("/check_access_token")
    fun checkAccessToken(
        @RequestHeader("Authorization")
        accessToken: String
    ): HashMap<String, Boolean> {
        return hashMapOf("is_valid" to authService.checkAccessToken(accessToken))
    }

    @PostMapping("/logout")
    fun logout(
        @RequestHeader("Authorization")
        accessToken: String
    ) {
        authService.logout(accessToken)
    }
}