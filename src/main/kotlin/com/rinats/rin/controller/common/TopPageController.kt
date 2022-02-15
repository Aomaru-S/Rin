package com.rinats.rin.controller.common

import com.rinats.rin.model.table.Employee
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
        return when (employeeService.getAuthority(employee.id ?: return "redirect:/login")) {
            0 -> "store_manager_index"
            1 -> "part_time_job_index"
            2 -> "store_terminal_index"
            else -> "redirect:/login"
        }
    }
}