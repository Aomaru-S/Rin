package com.rinats.rin.controller.storemanager

import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping

@Controller
@RequestMapping("/shift")
class ShiftController {
    @GetMapping("")
    fun index(): String {
        return "ThisMonthShiftCheck"
    }
}