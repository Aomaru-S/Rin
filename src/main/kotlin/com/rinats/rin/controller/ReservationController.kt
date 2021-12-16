package com.rinats.rin.controller

import com.rinats.rin.annotation.NonAuth
import com.rinats.rin.model.Employee
import com.rinats.rin.model.form.ReservationForm
import com.rinats.rin.service.ReservationService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.format.annotation.DateTimeFormat
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.validation.BindingResult
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestAttribute
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
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
        @RequestAttribute employee: Employee,
        request: HttpServletRequest,
        @Validated
        reservationForm: ReservationForm,
        bindingResult: BindingResult
    ): String {
        return when(
            reservationService.reservationRegistration(
            reservationForm.customerName ?: "",
            reservationForm.courseId ?: "",
            reservationForm.dateTime ?: LocalDateTime.now(),
            reservationForm.numOfPeople ?: 0,
            employee.employeeId,
            reservationForm.tableName ?: ""
            )
        ) {
            true -> "top"
            false -> "ReservationEntryError"
        }
    }

    @PostMapping("/reservation_registration_continue")
    fun reservationRegistrationContinue(): String {
        return "ReservationEntry"
    }
    @PostMapping("/reservation_cancel")
    fun reservationRegistrationCancel(): String {
        return "top"
    }

    @GetMapping("/reservation_check")
    fun reservationCheck(model: Model): String {
        model.addAttribute("reservationList", reservationService.getReservation())
        model.addAttribute("courseName", reservationService.getAllCourseName())
        return "ReservationCheck"
    }

    @PostMapping("/reservation_edit")
    fun reservationEdit(
        model: Model,
        @Validated
        reservationForm: ReservationForm,
        bindingResult: BindingResult
    ): String {
        model.apply {
            addAttribute("id", reservationForm.id)
            addAttribute("customerName", reservationForm.customerName)
            addAttribute("courseId", reservationForm.courseId)
            addAttribute("courseName", reservationService.getCourseName(reservationForm.courseId))
            addAttribute("dateTime", reservationForm.dateTime)
//            addAttribute("dateNow", LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm")))
            addAttribute("numOfPeople", reservationForm.numOfPeople)
            addAttribute("tableName", reservationForm.tableName)
            addAttribute("courseList",  reservationService.getCourse())
            addAttribute("tableList",  reservationService.getTable())
        }
        return "ReservationEditing"
    }

    @PostMapping("/reservation_edit_check")
    fun reservationEditCheck(
        model: Model,
        @Validated
        reservationForm: ReservationForm,
        bindingResult: BindingResult
    ): String {
        model.apply {
            addAttribute("id", reservationForm.id)
            addAttribute("customerName", reservationForm.customerName)
            addAttribute("courseId", reservationForm.courseId)
            addAttribute("courseName", reservationService.getCourseName(reservationForm.courseId))
            addAttribute("dateTime", reservationForm.dateTime)
            addAttribute("numOfPeople", reservationForm.numOfPeople)
            addAttribute("tableName", reservationForm.tableName)
        }
        return "ReservationEditingCheck"
    }

    @PostMapping("reservation_edit_complete")
    fun reservationEditComplete(
        @RequestAttribute employee: Employee,
        request: HttpServletRequest,
        @Validated
        reservationForm: ReservationForm,
        bindingResult: BindingResult
    ): String {
        return when (
            reservationService.reservationUpdate(
                reservationForm.id ?: "",
                reservationForm.customerName ?: "",
                reservationForm.courseId ?: "",
                reservationForm.dateTime ?: LocalDateTime.now(),
                reservationForm.numOfPeople ?: 0,
                employee.employeeId,
                reservationForm.tableName ?: ""
            )
        ) {
            true -> "top"
            false -> "ReservationEditError"
        }
    }
}