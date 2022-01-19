package com.rinats.rin.repository;

import com.rinats.rin.model.table.Retirement
import org.springframework.data.jpa.repository.JpaRepository

interface RetirementRepository1 : JpaRepository<Retirement, String> {
}