package com.rinats.rin.repository;

import com.rinats.rin.model.EmployeeLabor
import com.rinats.rin.model.compositeKey.EmployeeLaborId
import org.springframework.data.jpa.repository.JpaRepository

interface EmployeeLaborRepository : JpaRepository<EmployeeLabor, EmployeeLaborId> {
}