package com.rinats.rin.model.form

import javax.validation.constraints.Email
import javax.validation.constraints.NotBlank

data class ChangeMailAddressForm(
    @field:Email
    @field:NotBlank
    var mailAddress: String? = null
)