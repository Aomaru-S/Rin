package com.rinats.rin.dao

import com.rinats.rin.model.Employee
import com.rinats.rin.repository.EmployeeRepository
import org.springframework.beans.factory.annotation.Autowired

class TestDao(
    @Autowired
    private val employeeRepository: EmployeeRepository? = null
) {
    fun findAll(): MutableList<Employee>? {
        return employeeRepository?.findAll()
    }
}