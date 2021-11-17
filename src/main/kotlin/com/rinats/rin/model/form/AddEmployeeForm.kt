package com.rinats.rin.model.form

import javax.validation.constraints.Email
import javax.validation.constraints.NotBlank
import javax.validation.constraints.Size

data class AddEmployeeForm(
    @field:NotBlank
    @field:Size(max = 32)
    val firstName: String? = null,
    @field:NotBlank
    @field:Size(max = 32)
    val lastName: String? = null,
    val gender: Boolean? = null,
    @field:NotBlank
    val birthday: String? = null,
    @field:NotBlank
    @field:Email
    val mailAddress: String? = null
)
