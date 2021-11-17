package com.rinats.rin.controller.parttimejob

import com.rinats.rin.model.Employee
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import javax.servlet.http.HttpServletRequest

@RestController
@RequestMapping("/api/v1/shift")
class ShiftRestController {
    @PostMapping("submit")
    fun submitShift(request: HttpServletRequest) {
        val employee = request.getAttribute("employee") as Employee

    }
}