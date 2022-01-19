package com.rinats.rin.controller.parttimejob

import com.rinats.rin.model.table.Employee
import com.rinats.rin.service.AuthInfoService
import com.rinats.rin.service.EmployeeService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("api/employee")
class EmployeeRestController(
    @Autowired
    private val employeeService: EmployeeService,
    private val authInfoService: AuthInfoService
) {
    @PostMapping("/change_mailAddress")
    fun changeMailAddress(
        @RequestAttribute employee: Employee,
        mailAddress: String
    ): HashMap<String, Boolean> {
        val result = employeeService.changeMailAddress(employee.id ?: "", mailAddress)
        return hashMapOf("result" to result)
    }
}