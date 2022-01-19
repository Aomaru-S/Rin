package com.rinats.rin.repository

import com.rinats.rin.model.table.TentativeShiftHistory
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface TentativeShiftHistoryRepository : JpaRepository<TentativeShiftHistory, Int>