package com.rinats.rin.controller

import com.rinats.rin.model.Table
import com.rinats.rin.repository.TableRepository
import com.rinats.rin.service.TableCheckService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.validation.BindingResult
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.ModelAttribute
import org.springframework.web.bind.annotation.RestController

@RestController
class TableCheckController(
    @Autowired
    val tableCheckService: TableCheckService
) {
    @GetMapping("/tableCheck")
    fun tableCheck(): List<Table> {
        val aaa = tableCheckService.getTable()
        for (element in aaa) {
            println(element.name)
        }
        return aaa
    }
}