package com.rinats.rin.controller

import com.rinats.rin.dao.AuthDAO
import com.rinats.rin.model.form.AuthForm
import com.rinats.rin.repository.PasswordRepository
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
    val passwordRepository: PasswordRepository
) {
    @PostMapping("/login")
    fun login(
        @Validated
        @ModelAttribute
        authForm: AuthForm,
        validationResult: BindingResult
    ): UUID? {
        val authDao = AuthDAO(passwordRepository)
        val result = authDao.login(authForm.userId ?: "", authForm.password ?: "")
        if (validationResult.hasErrors() || !result) {
            return null
        }
        return UUID.randomUUID()
    }
}