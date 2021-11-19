package com.rinats.rin.model.form

import javax.validation.constraints.Max
import javax.validation.constraints.Min
import javax.validation.constraints.NotBlank

data class ShiftHopeForm(
    @NotBlank
    @Min(1)
    @Max(31)
    val date: Int
)