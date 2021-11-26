package com.rinats.rin.model.form

import javax.validation.constraints.Email
import javax.validation.constraints.NotBlank

data class ChangeMailAddressForm(
    @Email
    @NotBlank
    val mailAddress: String
)