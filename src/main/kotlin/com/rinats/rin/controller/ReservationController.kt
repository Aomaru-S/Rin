package com.rinats.rin.controller

import com.rinats.rin.model.Employee
import com.rinats.rin.model.form.ReservationForm
import com.rinats.rin.service.ReservationService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import java.time.LocalDateTime
import javax.servlet.http.HttpServletRequest

@Controller
class ReservationController (
    @Autowired
    val reservationService: ReservationService
) {

    @GetMapping("/reservation_entry")
    fun reservationEntry(model: Model): String {
        model.addAttribute("courseList",  reservationService.getCourse())
        model.addAttribute("tableList",  reservationService.getTable())
        return "ReservationEntry"
    }

    @PostMapping("/reservation_registration")
    fun reservationRegistration(
        request: HttpServletRequest,
        reservationForm: ReservationForm,
    ) {
        val employee = request.getAttribute("employee") as Employee
        reservationService.reservationRegistration(
            reservationForm.customerName ?: "",
            reservationForm.courseId ?: "",
            reservationForm.dateTime ?: LocalDateTime.now(),
            reservationForm.numOfPeople ?: 0,
            employee.employeeId,
            reservationForm.tableName ?: ""
        )
    }

    @GetMapping("/reservation_check")
    fun reservationCheck(model: Model): String {
        model.addAttribute("reservation", reservationService.getReservation())
        return "reservatonCheckPage"
    }

    @PostMapping("/reservation_edit")
    fun reservationEdit(model: Model, reservationForm: ReservationForm): String {
        model.apply {
            addAttribute("id", reservationForm.id)
            addAttribute("customerName", reservationForm.customerName)
            addAttribute("courseId", reservationForm.courseId)
            addAttribute("dateTime", reservationForm.dateTime)
            addAttribute("numOfPeople", reservationForm.numOfPeople)
            addAttribute("tableName", reservationForm.tableName)
        }
        return "ReservationEditPage"
    }

    @PostMapping("/reservation_edit_conf")
    fun reservationEditConf(model: Model, reservationForm: ReservationForm): String {
        model.apply {
            addAttribute("id", reservationForm.id)
            addAttribute("customerName", reservationForm.customerName)
            addAttribute("courseId", reservationForm.courseId)
            addAttribute("dateTime", reservationForm.dateTime)
            addAttribute("numOfPeople", reservationForm.numOfPeople)
            addAttribute("tableName", reservationForm.tableName)
        }
        return "ReservationEditConfPage"
    }

    @PostMapping("reservation_edit_complete")
    fun reservationEditComplete(model: Model, request: HttpServletRequest, reservationForm: ReservationForm): String {
        val employee = request.getAttribute("employee") as Employee
        reservationService.reservationUpdate(
            reservationForm.id ?: "",
            reservationForm.customerName ?: "",
            reservationForm.courseId ?: "",
            reservationForm.dateTime ?: LocalDateTime.now(),
            reservationForm.numOfPeople ?: 0,
            employee.employeeId,
            reservationForm.tableName ?: ""
        )
        return "ReservationEditCompletePage"
    }
}
