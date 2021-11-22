package com.rinats.rin.model.form

import javax.validation.constraints.Max
import javax.validation.constraints.Min

data class GetShiftsForm(
    @field:Min(1970) @field:Max(2100)
    val year: Int,
    @field:Min(1) @field:Max(12)
    val month: Int
)