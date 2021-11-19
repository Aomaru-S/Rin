package com.rinats.rin.controller

import com.rinats.rin.service.LaborService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.GetMapping

@Controller
class LaborController(
    @Autowired
    val laborService: LaborService
) {

    @GetMapping("labor_check")
    fun laborCheck(model: Model) {
        
    }
}