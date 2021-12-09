package com.rinats.rin.model.form

import javax.validation.constraints.Max
import javax.validation.constraints.Min
import javax.validation.constraints.NotBlank

data class EmployeeLevelForm(
    @field:NotBlank
    var employeeId: String? = null,
    @field:NotBlank
    var roleId: String? = null,
    @field:Min(1)
    @field:Max(5)
    var level: Int? = null
)