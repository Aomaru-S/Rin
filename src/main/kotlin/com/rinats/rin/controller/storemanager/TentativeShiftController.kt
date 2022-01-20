package com.rinats.rin.controller.storemanager

import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping

@Controller
@RequestMapping("/tentative_shift")
class TentativeShiftController {
    @GetMapping("/")
    fun index(): String {
        return "tentative_shift_index"
    }

    @GetMapping("/edit")
    fun editTentativeShift(): String {
        return "edit_tentative_shift"
    }
}