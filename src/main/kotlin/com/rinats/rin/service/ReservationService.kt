package com.rinats.rin.service

import com.rinats.rin.model.Reservation
import com.rinats.rin.repository.EmployeeRepository
import com.rinats.rin.repository.ReservationRepository
import com.rinats.rin.repository.TableRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import java.time.LocalDateTime

@Service
class ReservationService(
    @Autowired
    private val reservationRepository : ReservationRepository,
    private val tableRepository: TableRepository,
    private val employeeRepository: EmployeeRepository
) {
    fun reservationRegistration(
        customerName: String,
        courseId: String,
        dateTime: LocalDateTime,
        numOfPeople: Int,
        employeeId: String,
        tableId: String
    ): Boolean {
        if (tableRepository.findById(tableId).isEmpty) else return false
        val id = reservationRepository.findAll().last().id ?: "0"
        //employeeIdの取得が必要
        val reservation = Reservation(id, customerName, courseId,  dateTime, numOfPeople, employeeId, tableId)
        reservationRepository.save(reservation)
        return true
    }
}