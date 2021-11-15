package com.rinats.rin.service

import com.rinats.rin.model.Table
import com.rinats.rin.repository.TableRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class TableCheckService(
    @Autowired
    private val tableRepository: TableRepository
) {
    fun getTable(): List<Table> {
        return tableRepository.findAll()
    }
}