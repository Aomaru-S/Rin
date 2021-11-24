package com.rinats.rin.controller

import com.rinats.rin.model.form.HourlyWageForm
import com.rinats.rin.service.HourlyWageService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping

@Controller
class HourlyWageController(
    @Autowired
    val hourlyWageService: HourlyWageService
) {

    @GetMapping("/hourly_wage_check")
    fun hourlyWageCheck(model: Model): String {
        model.addAttribute(hourlyWageService.getHourlyWage())
        return "HourlyWageCheckPage"
    }

    @PostMapping("/hourly_wage_edit")
    fun hourlyWageEdit(model: Model, hourlyWageForm: HourlyWageForm): String {
        model.apply {
            addAttribute("employeeId", hourlyWageForm.employeeId)
            addAttribute("hourlyWage", hourlyWageForm.hourlyWage)
        }
        return "HourlyWageEditPage"
    }

    @PostMapping("/hourly_wage_conf")
    fun hourlyWageConf(model: Model, hourlyWageForm: HourlyWageForm): String {
        model.apply {
            addAttribute("employeeId", hourlyWageForm.employeeId)
            addAttribute("hourlyWage", hourlyWageForm.hourlyWage)
        }
        return "HourlyWageConfPage"
    }

    @PostMapping("/hourly_wage_complate")
    fun hourlyWageComplete(model: Model, hourlyWageForm: HourlyWageForm): String {
        hourlyWageService.hourlyWageUpdate(hourlyWageForm.employeeId ?: "", hourlyWageForm.hourlyWage ?: 800)
        return "HourlyWageCompletePage"
    }
}