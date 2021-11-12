package com.rinats.rin.service

import com.rinats.rin.model.Table
import com.rinats.rin.repository.TableRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class TableRegistrationService(
    @Autowired
    private val tableRepository: TableRepository
) {
    fun tableRegistration(name: String, numOfPeople: Int) : Boolean {
        val table = Table(name, numOfPeople)
        tableRepository.save(table)
        return true
    }
}