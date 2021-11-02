package com.rinats.rin.controller

import com.rinats.rin.model.Employee
import com.rinats.rin.repository.EmployeeRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class EmployeeController (
    @Autowired
    val employeeRepository: EmployeeRepository
) {

    @GetMapping("/employeeList1")
    fun employeeList(): MutableList<Employee> {
        return employeeRepository.findAll()
    }
}