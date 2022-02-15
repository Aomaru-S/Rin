package com.rinats.rin.controller

import com.rinats.rin.annotation.NonAuth
import com.rinats.rin.model.table.Employee
import com.rinats.rin.service.EmployeeService
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
class TestController(
    private val employeeService: EmployeeService
) {
    @NonAuth
    @GetMapping("/employee_test")
    fun test(
        @RequestParam("employee_id") employeeId: String,
    ): Employee? {
        return employeeService.getEmployee(employeeId, containTentative = true, containRetirement = true)
    }
}