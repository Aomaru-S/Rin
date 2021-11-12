package com.rinats.rin.model.form

import javax.validation.constraints.NotBlank

data class AuthForm(
    @field:NotBlank
    var userId: String? = null,
    @field:NotBlank
    var password: String? = null
)
