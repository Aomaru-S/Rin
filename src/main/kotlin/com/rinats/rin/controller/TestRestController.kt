package com.rinats.rin.controller

import com.rinats.rin.annotation.NonAuth
import com.rinats.rin.annotation.PartTimeJob
import com.rinats.rin.annotation.StoreManager
import com.rinats.rin.model.Employee
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.ModelAttribute
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import java.util.*
import javax.servlet.http.HttpServletRequest

@RestController
class TestRestController {
    @GetMapping("/returnEmployee")
    fun returnEmployee(request: HttpServletRequest, @ModelAttribute employee: Employee): Employee {
        val oEmployee = request.getAttribute("employee") as Optional<*>
        println(employee)
        return oEmployee.get() as Employee
    }

    @NonAuth
    @GetMapping("/")
    fun index(): String {
        return "Hello, World! This is public page."
    }

    @GetMapping("/private")
    fun private(): String {
        return "Hello, World! This is private page."
    }
}