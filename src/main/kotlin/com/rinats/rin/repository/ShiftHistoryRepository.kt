package com.rinats.rin.repository

import com.rinats.rin.model.ShiftHistory
import org.springframework.data.jpa.repository.JpaRepository

interface ShiftHistoryRepository : JpaRepository<ShiftHistory, Int>