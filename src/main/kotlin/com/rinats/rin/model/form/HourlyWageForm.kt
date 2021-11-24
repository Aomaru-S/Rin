package com.rinats.rin.model.form

import javax.validation.constraints.Max
import javax.validation.constraints.Min
import javax.validation.constraints.NotBlank

data class HourlyWageForm(
    @field:NotBlank
    var employeeId: String? = null,
    @Min(800)
    @Max(100000)
    var hourlyWage: Int? = null
)