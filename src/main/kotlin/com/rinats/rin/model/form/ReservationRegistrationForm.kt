package com.rinats.rin.model.form

import java.time.LocalDateTime
import javax.validation.constraints.Max
import javax.validation.constraints.Min
import javax.validation.constraints.NotBlank

data class ReservationRegistrationForm(
    @field:NotBlank
    var id: String? = null,
    @field:NotBlank
    var customerName: String? = null,
    var courseId: String? = null,
    var dateTime: LocalDateTime? = null,
    @Min(1)
    @Max(100)
    var numOfPeople: Int? = null,
    @field:NotBlank
    var employeeId: String? = null,
    @field:NotBlank
    var tableId: String? = null
)