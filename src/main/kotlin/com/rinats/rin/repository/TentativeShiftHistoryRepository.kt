package com.rinats.rin.repository

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface TentativeShiftHistoryRepository : JpaRepository<TentativeShiftHistoryRepository, Int>