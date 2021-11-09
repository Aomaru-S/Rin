package com.rinats.rin.repository

import com.rinats.rin.model.ShiftTemplate
import org.springframework.data.jpa.repository.JpaRepository

interface ShiftTemplateRepository : JpaRepository<ShiftTemplate, String>