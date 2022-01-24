package com.rinats.rin.model.form

import org.springframework.format.annotation.DateTimeFormat
import java.time.LocalDate
import javax.validation.constraints.*

data class AddEmployeeForm(
    @field:NotBlank
    @field:Size(max = 32)
    val firstName: String? = null,
    @field:NotBlank
    @field:Size(max = 32)
    val lastName: String? = null,
    @field:NotNull
    @field:Past
    @field:DateTimeFormat(pattern = "yyyy-MM-dd")
    val birthday: LocalDate? = null,
    @field:NotNull
    @field:Min(800)
    val hourlyWage: Int? = null,
    @field:NotBlank
    @field:Email
    val mailAddress: String? = null,
    @field:Min(0)
    @NotNull
    val genderId: Int? = null,
    @field:NotNull
    val isTaxable: Boolean? = null
)
