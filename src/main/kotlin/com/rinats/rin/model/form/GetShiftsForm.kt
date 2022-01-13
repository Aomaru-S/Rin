package com.rinats.rin.model.form

import javax.validation.constraints.Max
import javax.validation.constraints.Min
import javax.validation.constraints.NotNull

data class GetShiftsForm(
    @field:NotNull
    @field:Min(1970) @field:Max(2037)
    val year: Int?,
    @field:NotNull
    @field:Min(1) @field:Max(12)
    val month: Int?
)