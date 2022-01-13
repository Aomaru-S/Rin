package com.rinats.rin.model.form

import javax.validation.constraints.NotNull

data class GetShiftHopeForm(
    @field:NotNull
    val year: Int?,
    @field:NotNull
    val month: Int?
)