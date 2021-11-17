package com.rinats.rin.controller

import com.rinats.rin.model.form.ReservationRegistrationForm
import com.rinats.rin.service.ReservationService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Controller
import org.springframework.validation.BindingResult
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.ModelAttribute
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RestController
import java.time.LocalDateTime
import javax.servlet.http.HttpServletRequest

@Controller
class ReservationController (
    @Autowired
    val reservationService: ReservationService
) {
    @GetMapping("/reservation_registration")
    fun reservationRegistration(
        @Validated
        @ModelAttribute
        request: HttpServletRequest,
        reservationRegistrationForm: ReservationRegistrationForm,
        validationResult: BindingResult
    ) {
        reservationService.reservationRegistration(
            request,
            reservationRegistrationForm.customerName ?: "",
            reservationRegistrationForm.courseId ?: "",
            reservationRegistrationForm.dateTime ?: LocalDateTime.now(),
            reservationRegistrationForm.numOfPeople ?: 0,
            reservationRegistrationForm.tableId ?: ""
        )
    }
}
