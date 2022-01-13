package com.rinats.rin.repository

import com.rinats.rin.model.Shift
import com.rinats.rin.model.compositeKey.ShiftKey
import org.springframework.data.jpa.repository.JpaRepository

interface ShiftRepository : JpaRepository<Shift, ShiftKey> {
    fun findByEmployeeId(employeeId: String):List<Shift>
}