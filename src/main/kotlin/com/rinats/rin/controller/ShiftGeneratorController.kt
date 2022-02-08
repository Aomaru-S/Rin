package com.rinats.rin.controller

import com.rinats.rin.service.GetHolidaysJpApiService
import com.rinats.rin.service.ShiftGeneratorService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.PostMapping

@Controller
class ShiftGeneratorController(
    @Autowired
    private val shiftGeneratorService: ShiftGeneratorService,
    private val getHolidaysJpApiService: GetHolidaysJpApiService
) {

    @PostMapping("/shift_generate")
    fun shiftGenerator(): String {
//        shiftGeneratorService.shiftGenerator()
        return "redirect:/tentative_shift"
    }

    /*@GetMapping("/shift_generate_test")
    fun test(): Map<LocalDate, String>? {
        return getHolidaysJpApiService.getHolidaysJpApi(2021)
    }*/
}