package com.rinats.rin.controller

import com.rinats.rin.annotation.NonAuth
import com.rinats.rin.model.EmployeeLevel
import com.rinats.rin.service.EmployeeLevelService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class EmployeeLevelController(
    @Autowired
    val employeeLevelService: EmployeeLevelService
) {
    @NonAuth
    @GetMapping("employee_level_check")
    fun laborCheck(model: Model): MutableList<EmployeeLevel> {
        return employeeLevelService.getLabor()
    }
}