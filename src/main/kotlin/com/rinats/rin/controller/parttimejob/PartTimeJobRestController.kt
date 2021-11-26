package com.rinats.rin.controller.parttimejob

import com.rinats.rin.annotation.PartTimeJob
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.GetMapping
import javax.servlet.http.HttpServletRequest

@Controller
class PartTimeJobRestController {
    @PartTimeJob
    @GetMapping("/part_time_job_top")
    fun partTimeJobTop(request: HttpServletRequest): String {
        return "part_time_job_top"
    }}