package com.rinats.rin.service

import com.rinats.rin.model.Labor
import com.rinats.rin.model.compositeKey.LaborId
import com.rinats.rin.repository.EmployeeRepository
import com.rinats.rin.repository.LaborRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class LaborService(
    @Autowired
    private val laborRepository: LaborRepository,
    private val employeeRepository: EmployeeRepository
) {

    fun getLabor(): MutableList<Labor> {
        return laborRepository.findAll()
    }

    fun getByName(employeeId: String): String {
        val employee = employeeRepository.findById(employeeId)
        return employee.get().lastName + " " + employee.get().firstName
    }

    fun levelUpdate(employeeId: String, roleId: String, level: Int) {
        val laborId = LaborId(employeeId, roleId)
        val employee = employeeRepository.findById(employeeId)
        val labor = Labor()
        labor.id = laborId
        labor.level = level
        laborRepository.save(labor)
    }
}