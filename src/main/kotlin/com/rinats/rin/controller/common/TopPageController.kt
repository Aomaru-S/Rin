package com.rinats.rin.controller.common

import com.rinats.rin.model.Employee
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.GetMapping
import javax.servlet.http.HttpServletRequest

@Controller
class TopPageController(
) {
    @GetMapping("/")
    fun forwardIndex(request: HttpServletRequest): String {
        val employee = request.getAttribute("employee") as Employee
        if (employee.roleId == "1") {
            return "forward:store_manager_top"
        }
        return "forward:part_time_job_top"
    }
}