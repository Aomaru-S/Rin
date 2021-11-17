package com.rinats.rin.service

import com.rinats.rin.model.Table
import com.rinats.rin.repository.TableRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class TableService(@Autowired private val tableRepository: TableRepository) {

    fun tableRegistration(name: String, numOfPeople: Int) : Boolean {
        val table = Table(name, numOfPeople)
        tableRepository.save(table)
        return true
    }

    fun getTable(): List<Table> {
        return tableRepository.findAll()
    }

    fun tableUpdate(name: String, numOfPeople: Int) {
        val table = Table(name, numOfPeople)
        tableRepository.save(table)
    }
}