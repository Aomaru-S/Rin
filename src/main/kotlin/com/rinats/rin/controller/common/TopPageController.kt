package com.rinats.rin.controller.common

import com.rinats.rin.model.Employee
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestAttribute
import org.springframework.web.bind.annotation.SessionAttribute
import javax.servlet.http.HttpServletRequest

@Controller
class TopPageController {

    @GetMapping("/")
    fun forwardIndex(
        request: HttpServletRequest,
        @RequestAttribute
        employee: Employee
    ): String {
        println("Top page Top page Top page Top page Top page Top page")
        if (employee.roleId == "1") {
            return "store_manager_top"
        }
        return "part_time_job_top"
    }
}