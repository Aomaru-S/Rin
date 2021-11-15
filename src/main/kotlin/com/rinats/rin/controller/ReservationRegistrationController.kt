package com.rinats.rin.controller

import com.rinats.rin.model.form.ReservationRegistrationForm
import com.rinats.rin.service.ReservationRegistrationService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.validation.BindingResult
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.ModelAttribute
import org.springframework.web.bind.annotation.RestController
import java.time.LocalDateTime

@RestController
class ReservationRegistrationController (
    @Autowired
    val reservationRegistrationService: ReservationRegistrationService
) {
    @GetMapping("/ReservationRegistration")
    fun reservationRegistration(
        @Validated
        @ModelAttribute
        reservationRegistrationForm: ReservationRegistrationForm,
        validationResult: BindingResult
    ) {
        reservationRegistrationService.reservationRegistration(
            reservationRegistrationForm.customerName ?: "",
            reservationRegistrationForm.courseId ?: "",
            reservationRegistrationForm.dateTime ?: LocalDateTime.now(),
            reservationRegistrationForm.numOfPeople ?: 0,
            reservationRegistrationForm.employeeId ?: "",
            reservationRegistrationForm.tableId ?: ""
        )
    }
}
