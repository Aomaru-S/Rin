package com.rinats.rin.model.form

import javax.validation.constraints.NotBlank
import javax.validation.constraints.Size

data class GetEmployeeForm(
    @Size(min = 1, max = 6)
    @NotBlank
    val employeeId: String
)
