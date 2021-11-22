package com.rinats.rin.repository

import com.rinats.rin.model.ShiftHope
import com.rinats.rin.model.compositeKey.ShiftHopeKey
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.stereotype.Repository
import java.sql.Date

@Repository
interface ShiftHopeRepository : JpaRepository<ShiftHope, ShiftHopeKey> {
    fun findByEmployeeId(employeeId: String): List<ShiftHope>
}