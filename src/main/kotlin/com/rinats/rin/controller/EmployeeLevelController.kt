package com.rinats.rin.controller

import com.rinats.rin.annotation.NonAuth
import com.rinats.rin.model.EmployeeLevel
import com.rinats.rin.model.form.EmployeeLevelForm
import com.rinats.rin.service.EmployeeLevelService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RestController

@Controller
class EmployeeLevelController(
    @Autowired
    val employeeLevelService: EmployeeLevelService
) {

    @GetMapping("employee_level_check")
    fun employeeLevelCheck(model: Model): MutableList<EmployeeLevel> {
        return employeeLevelService.getLabor()
    }

    @PostMapping("employee_level_edit")
    fun employeeLevelEdit(model: Model, employeeLevelForm: EmployeeLevelForm): String {
        model.apply {
            addAttribute("id", employeeLevelForm.id)
            addAttribute("name", employeeLevelForm.level)
        }
        return "EmployeeLevelEditPage"
    }

    @PostMapping("employee_level_edit_conf")
    fun employeeLevelEditConf(model: Model, employeeLevelForm: EmployeeLevelForm): String {
        model.apply {
            addAttribute("id", employeeLevelForm.id)
            addAttribute("name", employeeLevelForm.level)
        }
        return "EmployeeLevelEditConfPage"
    }

    @PostMapping("employee_level_edit_complete")
    fun employeeLevelEditComplete(model: Model, employeeLevelForm: EmployeeLevelForm): String {
        employeeLevelService.levelUpdate(employeeLevelForm.id ?: "", employeeLevelForm.level ?: 1)
        return "EmployeeLevelEditCompletePage"
    }
}