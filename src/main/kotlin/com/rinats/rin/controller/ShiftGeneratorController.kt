package com.rinats.rin.controller

import com.rinats.rin.service.GetHolidaysJpApiService
import com.rinats.rin.service.ShiftGeneratorService
import com.rinats.rin.service.TentativeShiftService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestParam

@Controller
class ShiftGeneratorController(
    @Autowired
    private val shiftGeneratorService: ShiftGeneratorService,
    private val tentativeShiftService: TentativeShiftService
) {

    @PostMapping("/shift_generate")
    fun shiftGenerator(
        @RequestParam("delete_flag", required = false) deleteFlag: Boolean?,
    ): String {
        if (deleteFlag == true) {
            tentativeShiftService.deleteAll()
        }
        shiftGeneratorService.shiftGenerator()
        return "redirect:/tentative_shift"
    }

    /*@GetMapping("/shift_generate_test")
    fun test(): Map<LocalDate, String>? {
        return getHolidaysJpApiService.getHolidaysJpApi(2021)
    }*/
}