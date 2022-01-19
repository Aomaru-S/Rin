package com.rinats.rin.repository;

import com.rinats.rin.model.table.TotalSalary
import com.rinats.rin.model.table.compositeId.TotalSalaryId
import org.springframework.data.jpa.repository.JpaRepository

interface TotalSalaryRepository : JpaRepository<TotalSalary, TotalSalaryId> {
}