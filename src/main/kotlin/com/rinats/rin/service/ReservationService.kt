package com.rinats.rin.service

import com.rinats.rin.model.Employee
import com.rinats.rin.model.Reservation
import com.rinats.rin.repository.EmployeeRepository
import com.rinats.rin.repository.ReservationRepository
import com.rinats.rin.repository.TableRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import java.time.LocalDateTime
import javax.servlet.http.HttpServletRequest

@Service
class ReservationService(
    @Autowired
    private val reservationRepository : ReservationRepository,
    private val tableRepository: TableRepository
) {
    fun reservationRegistration(
        request: HttpServletRequest,
        customerName: String,
        courseId: String,
        dateTime: LocalDateTime,
        numOfPeople: Int,
        tableId: String
    ): Boolean {
        if (tableRepository.findById(tableId).isEmpty) else return false
        val id = reservationRepository.findAll().last().id ?: "0"
        val employeeId = request.getAttribute("employee") as Employee
        val reservation = Reservation(id, customerName, courseId,  dateTime, numOfPeople, employeeId.employeeId, tableId)
        reservationRepository.save(reservation)
        return true
    }
}