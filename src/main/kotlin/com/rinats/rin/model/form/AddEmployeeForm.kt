package com.rinats.rin.model.form

import javax.validation.constraints.NotBlank

data class AddEmployeeForm(
    @field:NotBlank
    val firstName: String? = null,
    @field:NotBlank
    val lastName: String? = null,
    val gender: Boolean? = null,
    @field:NotBlank
    val birthday: String? = null,
    @field:NotBlank
    val mailAddress: String? = null
)
