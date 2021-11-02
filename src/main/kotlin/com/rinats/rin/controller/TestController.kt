package com.rinats.rin.controller

import com.rinats.rin.model.Employee
import com.rinats.rin.model.Role
import com.rinats.rin.repository.EmployeeRepository
import com.rinats.rin.repository.RoleRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class TestController(
    @Autowired
    val roleRepository: RoleRepository,
    val employeeRepository: EmployeeRepository
) {

    @GetMapping("/")
    fun index(): MutableList<Role> {
        return roleRepository.findAll()
    }
}