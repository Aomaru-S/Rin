package com.rinats.rin.service

import com.rinats.rin.model.Course
import com.rinats.rin.model.Reservation
import com.rinats.rin.model.Table
import com.rinats.rin.repository.CourseRepository
import com.rinats.rin.repository.ReservationRepository
import com.rinats.rin.repository.TableRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import java.util.Date

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
        dateTime: Date,
        numOfPeople: Int,
        employeeId: String,
        tableName: String
    ) {
        if (reservationRepository.findAll().isNullOrEmpty()) {
            val reservation = Reservation("1", customerName, courseId,  dateTime, numOfPeople, employeeId, tableName)
            reservationRepository.save(reservation)
        } else {
            val id = reservationRepository.findAll().size + 1
            val reservation = Reservation(id.toString(), customerName, courseId,  dateTime, numOfPeople, employeeId, tableName)
            reservationRepository.save(reservation)
        }
    }

    fun getReservation(): List<Reservation> {
        return reservationRepository.findAll()
    }

    fun getAllCourseName(): List<String> {
        val list = mutableListOf<String>()
        for (i in 0 until reservationRepository.findAll().size) {
            list.add(i, courseRepository.findById(reservationRepository.findAll()[i].courseId).get().name)
        }
        return list
    }

    fun getCourseName(courseId: String?): String {
        return courseRepository.findById(courseId ?: "").get().name
    }

    fun reservationUpdate(
        id: String,
        customerName: String,
        courseId: String,
        dateTime: Date,
        numOfPeople: Int,
        employeeId: String,
        tableName: String
    ) {
        val reservation = Reservation(id, customerName, courseId,  dateTime, numOfPeople, employeeId, tableName)
        reservationRepository.save(reservation)
    }
}