package com.rinats.rin.model.form

import javax.validation.constraints.NotBlank

data class AuthForm(
    @field:NotBlank
    var employeeId: String? = null,
    @field:NotBlank
    var password: String? = null
)
