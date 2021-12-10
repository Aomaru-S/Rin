package com.rinats.rin.repository;

import com.rinats.rin.model.ShiftTemplate
import com.rinats.rin.model.compositeKey.ShiftTemplateId
import org.springframework.data.jpa.repository.JpaRepository

interface ShiftTemplateRepository : JpaRepository<ShiftTemplate, ShiftTemplateId> {
}