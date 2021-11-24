package com.rinats.rin.controller.parttimejob

import com.rinats.rin.model.Employee
import com.rinats.rin.service.EmployeeService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestAttribute
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
class EmployeeRestController(
    @Autowired
    private val employeeService: EmployeeService
) {
    @PostMapping("change_password")
    fun changePassword(
        @RequestAttribute employee: Employee,
        @RequestParam(name = "mail_address") mailAddress: String
    ) {
        employeeService.changeMailAddress(employee.employeeId, mailAddress)
    }
}