package com.rinats.rin.service

import com.rinats.rin.model.EmployeeLevel
import com.rinats.rin.repository.EmployeeLevelRepository
import com.rinats.rin.repository.EmployeeRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class EmployeeLevelService(
    @Autowired
    private val employeeLevelRepository: EmployeeLevelRepository,
    private val employeeRepository: EmployeeRepository
) {

    fun getLabor(employeeId: String) {
        val employee = employeeRepository.findById(employeeId).get()
        val employeeLevel = EmployeeLevel(employee, 1)
    }
}