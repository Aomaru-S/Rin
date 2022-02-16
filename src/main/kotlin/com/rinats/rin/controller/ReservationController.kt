package com.rinats.rin.controller

import com.rinats.rin.model.table.Employee
import com.rinats.rin.model.form.ReservationForm
import com.rinats.rin.service.ReservationService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.validation.BindingResult
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestAttribute
import java.time.LocalDateTime
import javax.servlet.http.HttpServletRequest

@Controller
class ReservationController (
    @Autowired
    val reservationService: ReservationService
) {

    @GetMapping("/reservation_registration")
    fun reservationRegistration(model: Model): String {
        model.addAttribute("courseList",  reservationService.getCourse())
        model.addAttribute("tableList",  reservationService.getTable())
        return "ReservationRegistration"
    }

    @PostMapping("/reservation_registration_complete")
    fun reservationRegistrationComplete(
        @RequestAttribute employee: Employee,
        request: HttpServletRequest,
        @Validated
        reservationForm: ReservationForm,
        bindingResult: BindingResult
    ): String {
        reservationService.reservationRegistration(
        reservationForm.customerName ?: "",
        reservationForm.courseId ?: "",
        reservationForm.dateTime ?: LocalDateTime.now(),
        reservationForm.numOfPeople ?: 0,
        employee.id ?: "",
        reservationForm.tableName ?: ""
        )
        return "redirect:reservation_check"
    }

    @PostMapping("/reservation_registration_continue")
    fun reservationRegistrationContinue(): String {
        return "ReservationRegistration"
    }

    @PostMapping("/reservation_cancel")
    fun reservationRegistrationCancel(): String {
        return "redirect:/reservation_check"
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
        return "ReservationEdit"
    }

    @PostMapping("reservation_edit_complete")
    fun reservationEditComplete(
        @RequestAttribute
        employee: Employee,
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
                request.getParameter("oldDateTime") ?: "",
                reservationForm.dateTime ?: LocalDateTime.now(),
                reservationForm.numOfPeople ?: 0,
                employee.id ?: "",
                reservationForm.tableName ?: ""
            )
        ) {
            true -> "redirect:/reservation_check"
            false -> "ReservationEditError"
        }
    }

    @PostMapping("reservation_delete")
    fun reservationDelete(
        @RequestAttribute employee: Employee,
        request: HttpServletRequest,
        @Validated
        reservationForm: ReservationForm,
        bindingResult: BindingResult,
        model: Model
    ): String {
        reservationService.reservationDelete(
            reservationForm.id ?: "",
            reservationForm.customerName ?: "",
            reservationForm.courseId ?: "",
            reservationForm.dateTime ?: LocalDateTime.now(),
            reservationForm.numOfPeople ?: 0,
            employee.id ?: "",
            reservationForm.tableName ?: ""
        )
        return "redirect:/reservation_check"
    }
}