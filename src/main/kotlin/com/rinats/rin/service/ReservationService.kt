package com.rinats.rin.service

import com.rinats.rin.model.Course
import com.rinats.rin.model.Reservation
import com.rinats.rin.model.Table
import com.rinats.rin.repository.CourseRepository
import com.rinats.rin.repository.ReservationRepository
import com.rinats.rin.repository.TableRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import java.time.LocalDateTime

@Service
class ReservationService(
    @Autowired
    private val reservationRepository : ReservationRepository,
    private val courseRepository: CourseRepository,
    private val tableRepository: TableRepository
) {

    fun getCourse(): List<Course> {
        return courseRepository.findAll()
    }

    fun getTable(): List<Table> {
        return tableRepository.findAll()
    }

    fun reservationRegistration(
        customerName: String,
        courseId: String,
        dateTime: LocalDateTime,
        numOfPeople: Int,
        employeeId: String,
        tableName: String
    ) {
        println(customerName)
        if (reservationRepository.findAll().isNullOrEmpty()) {
            val reservation = Reservation("1", customerName, courseId,  dateTime, numOfPeople, employeeId, tableName)
            reservationRepository.save(reservation)
        } else {
            val id = Integer.parseInt(reservationRepository.findAll().last().id) + 1
            val reservation = Reservation(id.toString(), customerName, courseId,  dateTime, numOfPeople, employeeId, tableName)
            reservationRepository.save(reservation)
        }
    }

    fun getReservation(): List<Reservation> {
        return reservationRepository.findAll()
    }

    fun reservationUpdate(
        id: String,
        customerName: String,
        courseId: String,
        dateTime: LocalDateTime,
        numOfPeople: Int,
        employeeId: String,
        tableName: String
    ) {
        val reservation = Reservation(id, customerName, courseId,  dateTime, numOfPeople, employeeId, tableName)
        reservationRepository.save(reservation)
    }
}