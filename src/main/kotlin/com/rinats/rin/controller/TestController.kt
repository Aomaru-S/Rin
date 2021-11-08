package com.rinats.rin.controller

import com.rinats.rin.dao.TestDao
import com.rinats.rin.model.Employee
import com.rinats.rin.model.Role
import com.rinats.rin.repository.EmployeeRepository
import com.rinats.rin.repository.RoleRepository
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.context.support.WebApplicationContextUtils


@RestController
class TestController(
    private val roleRepository: RoleRepository,
    private val employeeRepository: EmployeeRepository? = null
) {

    @GetMapping("/")
    fun index(): MutableList<Employee>? {
//        return employeeRepository?.findAll()
        return TestDao(employeeRepository).findAll()
    }

    @GetMapping("/2")
    fun index2(): MutableList<Role> {
        return roleRepository.findAll()
    }
}