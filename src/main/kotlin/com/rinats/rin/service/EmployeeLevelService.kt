package com.rinats.rin.service

import com.rinats.rin.model.Employee
import com.rinats.rin.model.EmployeeLevel
import com.rinats.rin.repository.EmployeeLevelRepository
import com.rinats.rin.repository.EmployeeRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class EmployeeLevelService(
    @Autowired
    private val employeeLevelRepository: EmployeeLevelRepository
) {

    fun getLabor(): MutableList<EmployeeLevel> {
        return employeeLevelRepository.findAll()
    }

    fun levelUpdate(employeeId: String, level: Int) {
        val employee = employeeLevelRepository.findById(employeeId).get().employee
        val level = EmployeeLevel(employeeId, employee, level)
        employeeLevelRepository.save(level)
    }
}