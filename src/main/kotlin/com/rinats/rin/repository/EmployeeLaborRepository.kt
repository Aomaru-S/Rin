package com.rinats.rin.repository;

import com.rinats.rin.model.table.EmployeeLabor
import com.rinats.rin.model.table.compositeId.EmployeeLaborId
import org.springframework.data.jpa.repository.JpaRepository

interface EmployeeLaborRepository : JpaRepository<EmployeeLabor, EmployeeLaborId> {
    fun findById_EmployeeId(employeeId: String): List<EmployeeLabor>
}