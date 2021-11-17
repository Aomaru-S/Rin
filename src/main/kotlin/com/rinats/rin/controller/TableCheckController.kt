package com.rinats.rin.controller

import com.fasterxml.jackson.core.JsonProcessingException
import com.fasterxml.jackson.databind.ObjectMapper
import com.rinats.rin.annotation.NonAuth
import com.rinats.rin.model.form.TableRegistrationForm
import com.rinats.rin.service.TableCheckService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.GetMapping

@Controller
class TableCheckController(
    @Autowired
    val tableCheckService: TableCheckService
) {
    @NonAuth
//    @GetMapping("/table_check")
    fun tableCheck(model: Model): String {
        model.addAttribute("tableList", tableCheckService.getTable())
        return "TableCheckPage"
    }
}