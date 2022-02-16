package com.rinats.rin.controller.parttimejob

import com.rinats.rin.annotation.NonAuth
import com.rinats.rin.model.form.EmployeeForm
import com.rinats.rin.model.table.Employee
import com.rinats.rin.service.AuthInfoService
import com.rinats.rin.service.EmployeeService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.validation.BindingResult
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.*
import java.net.http.HttpRequest
import javax.servlet.http.HttpServletRequest

@RestController
@RequestMapping("api/employee")
class EmployeeRestController(
    @Autowired
    private val employeeService: EmployeeService
) {

    @PostMapping("/get_employee")
    fun getEmployee(
        @RequestAttribute employee: Employee
    ): Employee? {
        return employeeService.getEmployee(employee.id)
    }

    @PostMapping("/change_mailAddress")
    fun changeMailAddress(
        @RequestAttribute employee: Employee,
        mailAddress: String
    ): HashMap<String, Boolean> {
        val result = employeeService.changeMailAddress(employee.id ?: "", mailAddress)
        return hashMapOf("result" to result)
    }
}