package com.rinats.rin.repository;

import com.rinats.rin.model.Labor
import com.rinats.rin.model.LaborId
import org.springframework.data.jpa.repository.JpaRepository

interface LaborRepository : JpaRepository<Labor, LaborId> {
    fun findById_EmployeeId(employeeId: String): List<Labor>
}