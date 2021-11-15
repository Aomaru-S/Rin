package com.rinats.rin.controller

import com.rinats.rin.model.Table
import com.rinats.rin.service.TableCheckService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class TableCheckController(
    @Autowired
    val tableCheckService: TableCheckService
) {
    @PostMapping("/table_check")
    fun tableCheck(): List<Table> {
        return tableCheckService.getTable()
    }
}