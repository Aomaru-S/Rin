package com.rinats.rin.model.form

import javax.validation.constraints.Email
import javax.validation.constraints.NotBlank

data class ForgetPasswordForm(
    @NotBlank
    val employeeId: String,
    @NotBlank
    @Email
    val mailAddress: String
)