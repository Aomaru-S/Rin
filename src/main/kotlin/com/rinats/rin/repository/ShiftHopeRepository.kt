package com.rinats.rin.repository

import com.rinats.rin.model.ShiftHope
import com.rinats.rin.model.compositeKey.ShiftHopeKey
import org.springframework.data.jpa.repository.JpaRepository

interface ShiftHopeRepository : JpaRepository<ShiftHope, ShiftHopeKey>