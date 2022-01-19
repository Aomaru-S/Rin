package com.rinats.rin.repository

import com.rinats.rin.model.table.ShiftHope
import com.rinats.rin.model.table.compositeId.ShiftHopeId
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.time.LocalDate

@Repository
interface ShiftHopeRepository : JpaRepository<ShiftHope, ShiftHopeId> {
    fun findById_EmployeeId(employeeId: String): List<ShiftHope>
    fun deleteById_EmployeeId(employeeId: String): Boolean
    fun findById_ShiftDateBetween(start_shiftDate: LocalDate, last_shiftDate: LocalDate): List<ShiftHope>
    //@Query("SELECT * FROM shift_hope WHERE date >= %:start%")
    //fun findByDate(@Param("start") start: Date): List<ShiftHope>
}