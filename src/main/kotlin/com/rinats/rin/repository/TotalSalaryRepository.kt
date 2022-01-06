package com.rinats.rin.repository;

import com.rinats.rin.model.TotalSalary
import com.rinats.rin.model.compositeKey.TotalSalaryId
import org.springframework.data.jpa.repository.JpaRepository

interface TotalSalaryRepository : JpaRepository<TotalSalary, TotalSalaryId> {
}