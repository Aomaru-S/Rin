package com.rinats.rin.repository;

import com.rinats.rin.model.table.ShiftTemplate
import com.rinats.rin.model.table.compositeId.ShiftTemplateId
import org.springframework.data.jpa.repository.JpaRepository
import java.util.*

interface ShiftTemplateRepository : JpaRepository<ShiftTemplate, ShiftTemplateId> {
//    fun countById_RoleIdAndWeeksHolidayName(roleId: Int, weeksHolidayName: String): Int
    fun findById_RoleIdAndWeeksHolidayName(roleId: Int, weeksHolidayName: String): Optional<ShiftTemplate>
}