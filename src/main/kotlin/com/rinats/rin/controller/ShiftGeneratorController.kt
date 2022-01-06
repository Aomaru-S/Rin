package com.rinats.rin.controller

import com.alias.kh.shiftgenerator.service.ShiftGeneratorService
import com.rinats.rin.service.GetHolidaysJpApiService
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

    @GetMapping("/")
    fun shiftGenerator(): String {
        shiftGeneratorService.shiftGenerator()
        return "executed!!!"
    }

    @GetMapping("/test")
    fun test(): Map<LocalDate, String>? {
        return getHolidaysJpApiService.getHolidaysJpApi(2021)
    }
}