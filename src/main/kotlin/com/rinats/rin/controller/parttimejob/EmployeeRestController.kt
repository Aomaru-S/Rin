package com.rinats.rin.controller.parttimejob

import com.rinats.rin.model.Employee
import com.rinats.rin.repository.AuthInfoRepository
import com.rinats.rin.service.EmployeeService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestAttribute
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController("api/v1/employee")
class EmployeeRestController(
    @Autowired
    private val employeeService: EmployeeService,
    private val authInfoRepository: AuthInfoRepository
) {
    @PostMapping("/change_mailAddress")
    fun changeMailAddress(
        @RequestAttribute employee: Employee,
        @RequestParam(name = "mail_address") mailAddress: String
    ) {
        employeeService.changeMailAddress(employee.employeeId, mailAddress)
    }

    @PostMapping("/change_password")
    fun changePassword(
        @RequestAttribute employee: Employee,
        @RequestParam oldPassword: String,
        @RequestParam newPassword: String
    ): HashMap<String, Boolean> {
        val authInfo = authInfoRepository.findById(employee.employeeId).orElse(null) ?: return hashMapOf("result" to false)
        val result = employeeService.changePassword(authInfo, oldPassword, newPassword)
        return hashMapOf("result" to result)
    }
}