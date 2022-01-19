package com.rinats.rin.repository;

import com.rinats.rin.model.table.ShiftTemplate
import com.rinats.rin.model.table.compositeId.ShiftTemplateId
import org.springframework.data.jpa.repository.JpaRepository

interface ShiftTemplateRepository : JpaRepository<ShiftTemplate, ShiftTemplateId> {
}