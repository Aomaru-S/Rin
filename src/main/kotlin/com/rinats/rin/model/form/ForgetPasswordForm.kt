package com.rinats.rin.model.form

import javax.validation.constraints.Email
import javax.validation.constraints.NotBlank

data class ForgetPasswordForm(
    @field:NotBlank
    var employeeId: String? = null,
    @field:NotBlank
    @field:Email
    var mailAddress: String? = null
)