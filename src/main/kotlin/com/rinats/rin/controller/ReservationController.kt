package com.rinats.rin.controller

import com.rinats.rin.model.form.ReservationRegistrationForm
import com.rinats.rin.service.ReservationService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.validation.BindingResult
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.ModelAttribute
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RestController
import java.time.LocalDateTime

@RestController
class ReservationController (
    @Autowired
    val reservationService: ReservationService
) {
    @PostMapping("/reservation_registration")
    fun reservationRegistration(
        @Validated
        @ModelAttribute
        reservationRegistrationForm: ReservationRegistrationForm,
        validationResult: BindingResult
    ) {
        reservationService.reservationRegistration(
            reservationRegistrationForm.customerName ?: "",
            reservationRegistrationForm.courseId ?: "",
            reservationRegistrationForm.dateTime ?: LocalDateTime.now(),
            reservationRegistrationForm.numOfPeople ?: 0,
            reservationRegistrationForm.employeeId ?: "",
            reservationRegistrationForm.tableId ?: ""
        )
    }
}
