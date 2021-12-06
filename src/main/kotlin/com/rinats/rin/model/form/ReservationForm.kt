package com.rinats.rin.model.form

import org.springframework.format.annotation.DateTimeFormat
import java.time.LocalDateTime
import javax.validation.constraints.Max
import javax.validation.constraints.Min
import javax.validation.constraints.NotBlank

data class ReservationForm(
    @field:NotBlank
    var id: String? = null,
    @field:NotBlank
    var customerName: String? = null,
    @field:NotBlank
    var courseId: String? = null,
    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm")
    var dateTime: LocalDateTime? = null,
    @Min(1)
    @Max(100)
    var numOfPeople: Int? = null,
    @field:NotBlank
    var employeeId: String? = null,
    @field:NotBlank
    var tableName: String? = null
)