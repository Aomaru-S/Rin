package com.rinats.rin.service

import com.rinats.rin.model.Reservation
import com.rinats.rin.repository.ReservationRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import java.time.LocalDateTime

@Service
class ReservationRegistrationService(
    @Autowired
    private val reservationRepository : ReservationRepository
) {
    fun reservationRegistration(
        customerName: String,
        courseId: String,
        dateTime: LocalDateTime,
        numOfPeople: Int,
        employeeId: String,
        tableId: String
    ): Boolean {
        val reservation = Reservation("id", customerName, courseId,  dateTime, numOfPeople, employeeId, tableId)
        reservationRepository.save(reservation)
        return true
    }
}