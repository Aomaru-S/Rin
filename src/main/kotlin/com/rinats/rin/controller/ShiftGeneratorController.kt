package com.rinats.rin.controller

import com.rinats.rin.service.GetHolidaysJpApiService
import com.rinats.rin.service.ShiftGeneratorService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController
import java.time.LocalDate

@RestController
class ShiftGeneratorController(
    @Autowired
    private val shiftGeneratorService: ShiftGeneratorService,
    private val getHolidaysJpApiService: GetHolidaysJpApiService
) {

    @GetMapping("/shift_generate")
    fun shiftGenerator(): String {
        shiftGeneratorService.shiftGenerator()
        return "executed!!!"
    }

    @GetMapping("/shift_generate_test")
    fun test(): Map<LocalDate, String>? {
        return getHolidaysJpApiService.getHolidaysJpApi(2021)
    }
}