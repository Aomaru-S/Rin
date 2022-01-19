package com.rinats.rin.model.form

import javax.validation.constraints.NotBlank

data class ChangePasswordForm(
    @field:NotBlank
    val oldPassword: String? = null,
    @field:NotBlank
    val newPassword1: String? = null,
    @field:NotBlank
    val newPassword2: String? = null
)
