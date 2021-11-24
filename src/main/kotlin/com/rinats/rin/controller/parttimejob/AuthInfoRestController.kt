package com.rinats.rin.controller.parttimejob

import com.rinats.rin.model.Employee
import com.rinats.rin.service.AuthInfoService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestAttribute
import org.springframework.web.bind.annotation.RequestParam

@Controller
class AuthInfoRestController(
    @Autowired
    private val authInfoService: AuthInfoService
) {
    @PostMapping("/password")
    fun changePassword(
        @RequestAttribute employee: Employee,
        @RequestParam(name = "employee_id") employeeId: String,
        @RequestParam oldPassword: String,
        @RequestParam newPassword: String
    ): Boolean {
        return authInfoService.changePassword(employee.employeeId, oldPassword, newPassword)
    }
}