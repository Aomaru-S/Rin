package com.rinats.rin.controller.common

import com.rinats.rin.model.Employee
import com.rinats.rin.service.EmployeeService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestAttribute
import javax.servlet.http.HttpServletRequest

@Controller
class TopPageController(
    @Autowired
    private val employeeService: EmployeeService
) {

    @GetMapping("/")
    fun forwardIndex(
        request: HttpServletRequest,
        @RequestAttribute
        employee: Employee
    ): String {
        if (employeeService.getAuthority(employee.id ?: "") == 0) {
            return "store_manager_top"
        }
        return "part_time_job_top"
    }
}