package com.rinats.rin.controller

import com.rinats.rin.annotation.NonAuth
import com.rinats.rin.model.Employee
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController
import java.util.*
import javax.servlet.http.HttpServletRequest

@RestController
class TestRestController {
    @GetMapping("/returnEmployee")
    fun returnEmployee(request: HttpServletRequest): Employee {
        val oEmployee = request.getAttribute("employee") as Optional<*>
        return oEmployee.get() as Employee
    }

    @NonAuth
    @GetMapping("/")
    fun index(): String {
        return "Hello, World! This is public page"
    }
}