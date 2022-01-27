package com.rinats.rin.controller.storemanager

import com.rinats.rin.model.form.HourlyWageForm
import com.rinats.rin.service.HourlyWageService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import javax.servlet.http.HttpServletRequest

@Controller
class HourlyWageController(
    @Autowired
    val hourlyWageService: HourlyWageService
) {

    @GetMapping("/hourly_wage_check")
    fun hourlyWageCheck(model: Model): String {
        model.addAttribute("hourlyWageList", hourlyWageService.getHourlyWage())
        return "HourlyWageCheck"
    }

    @PostMapping("/hourly_wage_edit")
    fun hourlyWageEdit(model: Model, hourlyWageForm: HourlyWageForm, request: HttpServletRequest): String {
        model.apply {
            addAttribute("employeeId", hourlyWageForm.employeeId)
            addAttribute("name", request.getParameter("name"))
            addAttribute("hourlyWage", hourlyWageForm.hourlyWage)
        }
        return "HourlyWageEdit"
    }

    @PostMapping("/hourly_wage_edit_conf")
    fun hourlyWageEditConf(model: Model, hourlyWageForm: HourlyWageForm, request: HttpServletRequest): String {
        model.apply {
            addAttribute("employeeId", hourlyWageForm.employeeId)
            addAttribute("name", request.getParameter("name"))
            addAttribute("hourlyWage", hourlyWageForm.hourlyWage)
        }
        return "HourlyWageEditConf"
    }

    @PostMapping("/hourly_wage_complate")
    fun hourlyWageComplete(model: Model, hourlyWageForm: HourlyWageForm): String {
        hourlyWageService.hourlyWageUpdate(hourlyWageForm.employeeId ?: "", hourlyWageForm.hourlyWage ?: 800)
        return "redirect:hourly_wage_check"
    }
}