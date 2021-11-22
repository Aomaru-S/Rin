package com.rinats.rin.controller

import com.rinats.rin.service.EmployeeLevelService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.GetMapping

@Controller
class EmployeeLevelController(
    @Autowired
    val employeeLevelService: EmployeeLevelService
) {

    @GetMapping("employee_level_check")
    fun laborCheck(model: Model) {
    }
}