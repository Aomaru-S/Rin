package com.rinats.rin.model.form

import javax.validation.constraints.Max
import javax.validation.constraints.Min
import javax.validation.constraints.NotBlank

data class TableRegistrationForm(
    @field:NotBlank
    var name: String? = null,
    @Min(1)
    @Max(100)
    var numOfPeople: Int
)