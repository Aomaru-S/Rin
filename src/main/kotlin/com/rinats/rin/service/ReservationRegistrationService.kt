package com.rinats.rin.service

import com.rinats.rin.repository.ReservationRepository
import org.springframework.beans.factory.annotation.Autowired

class ReservationRegistrationService(
    @Autowired
    private val reservationRepository : ReservationRepository
) {
    fun reservationRegistration(
        id: String,
        customerName: String,
        courseId: String,
        dateTime: String,
        numOfPeople: Int,
        employeeId: String,
        tableId: String
    ) {

    }
}