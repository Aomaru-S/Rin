package com.rinats.rin.controller

import com.rinats.rin.model.Employee
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController
import javax.servlet.http.HttpServletRequest

@RestController
class TestRestController {
    @GetMapping("/returnEmployee2")
    fun returnEmployee(request: HttpServletRequest): Employee {
        return request.getAttribute("employee") as Employee
    }
}