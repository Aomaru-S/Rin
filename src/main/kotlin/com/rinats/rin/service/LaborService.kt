package com.rinats.rin.service

import com.rinats.rin.model.Employee
import com.rinats.rin.model.EmployeeLevel
import com.rinats.rin.repository.EmployeeLevelRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Repository
import org.springframework.stereotype.Service

@Service
class LaborService(@Autowired private val employeeLevelRepository: EmployeeLevelRepository) {

    fun getLabor() {
    }
}