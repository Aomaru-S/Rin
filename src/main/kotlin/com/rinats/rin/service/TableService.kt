package com.rinats.rin.service

import com.rinats.rin.model.table.Table
import com.rinats.rin.repository.TableRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class TableService(@Autowired private val tableRepository: TableRepository) {

    fun getTable(): List<Table> {
        return tableRepository.findAll()
    }

    fun tableUpdate(name: String, numOfPeople: Int) {
        val table = Table(name, numOfPeople)
        tableRepository.save(table)
    }

    fun tableDelete(name: String, numOfPeople: Int) {
        val table = Table(name, numOfPeople)
        tableRepository.delete(table)
    }
}