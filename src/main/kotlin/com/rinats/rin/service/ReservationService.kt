package com.rinats.rin.service

import com.rinats.rin.model.table.Course
import com.rinats.rin.model.table.Reservation
import com.rinats.rin.model.table.Table
import com.rinats.rin.repository.CourseRepository
import com.rinats.rin.repository.ReservationRepository
import com.rinats.rin.repository.TableRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.domain.Sort
import org.springframework.stereotype.Service
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

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
        if (reservationRepository.findAll().isEmpty()) {
             if (searchDuplicate(dateTime, tableName)) return
            val reservation = Reservation("1", customerName, courseId,  dateTime, numOfPeople, employeeId, tableName)
            reservationRepository.save(reservation)
        } else {
            if (searchDuplicate(dateTime, tableName)) return
            val id = reservationRepository.findAll().size + 1
            val reservation = Reservation(id.toString(), customerName, courseId,  dateTime, numOfPeople, employeeId, tableName)
            reservationRepository.save(reservation)
        }
    }

    fun getReservation(): List<Reservation> {
        return reservationRepository.findAll(Sort.by(Sort.Direction.ASC, "dateTime"))
    }

    fun getAllCourseName(): List<String> {
        val list = mutableListOf<String>()
        for (i in 0 until reservationRepository.findAll().size) {
            list.add(i, courseRepository.findById(reservationRepository.findAll(Sort.by(Sort.Direction.ASC, "dateTime"))[i].courseId).get().name)
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
        oldDateTime: String,
        dateTime: LocalDateTime,
        numOfPeople: Int,
        employeeId: String,
        tableName: String
    ): Boolean {
        if (searchDuplicate(dateTime, tableName)) {
            if(dateTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd")) == LocalDateTime.parse(oldDateTime).format(DateTimeFormatter.ofPattern("yyyy-MM-dd"))) {
                val reservation = Reservation(id, customerName, courseId,  dateTime, numOfPeople, employeeId, tableName)
                reservationRepository.save(reservation)
                return true
            } else {
                return false
            }
        }
        val reservation = Reservation(id, customerName, courseId,  dateTime, numOfPeople, employeeId, tableName)
        reservationRepository.save(reservation)
        return true
    }

    fun reservationDelete(
        id: String,
        customerName: String,
        courseId: String,
        dateTime: LocalDateTime,
        numOfPeople: Int,
        employeeId: String,
        tableName: String
    ) {
        val reservation = Reservation(id, customerName, courseId,  dateTime, numOfPeople, employeeId, tableName)
        reservationRepository.delete(reservation)
    }

    fun searchDuplicate(dateTime: LocalDateTime, tableName: String): Boolean {
        return reservationRepository.findAll().any {
            it.dateTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd")) == dateTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"))
                    && it.tableName == tableName
        }
    }

}