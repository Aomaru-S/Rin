package com.rinats.rin.model.form

import javax.validation.constraints.NotBlank

data class ChangePasswordForm(
    @field:NotBlank
    val oldPassword: String,
    @field:NotBlank
    val newPassword: String,
)
