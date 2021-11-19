package com.rinats.rin.model.form

import javax.validation.constraints.Max
import javax.validation.constraints.Min
import javax.validation.constraints.NotBlank

data class CourseForm(
    @Min(1)
    @Max(9999)
    var id: Int? = null,
    @field:NotBlank
    var name: String? = null,
)
