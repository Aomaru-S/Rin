package com.rinats.rin.service

import com.rinats.rin.model.table.Employee
import com.rinats.rin.repository.EmployeeRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class HourlyWageService(
    @Autowired
    private val employeeService: EmployeeService,
    private val employeeRepository: EmployeeRepository
) {

    fun getHourlyWage(): List<Employee> {
        return employeeService.getEmployeeList(containerManager = false)
    }

    fun hourlyWageUpdate(employeeId: String, hourlyWage: Int) {
        val employee = employeeService.getEmployee(employeeId)
            ?: throw IllegalStateException("Can't find employee from employeeId")
        employee.hourlyWage = hourlyWage
        employeeRepository.save(employee)
    }
}