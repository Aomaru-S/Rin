package com.rinats.rin.service

import com.rinats.rin.model.table.Employee
import com.rinats.rin.repository.EmployeeSer
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class HourlyWageService(
    @Autowired
    private val employeeRepository: EmployeeSer
) {

    fun getHourlyWage(): MutableList<Employee> {
        return employeeRepository.findAll()
    }

    fun hourlyWageUpdate(employeeId: String, hourlyWage: Int) {
        val employee = employeeRepository.findById(employeeId).orElse(null) ?: return
        employee.hourlyWage = hourlyWage
        employeeRepository.save(employee)
    }
}