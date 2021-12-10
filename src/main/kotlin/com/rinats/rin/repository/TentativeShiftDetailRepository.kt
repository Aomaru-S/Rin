package com.rinats.rin.repository;

import com.rinats.rin.model.TentativeShiftDetail
import org.springframework.data.jpa.repository.JpaRepository
import java.time.LocalDate

interface TentativeShiftDetailRepository : JpaRepository<TentativeShiftDetail, LocalDate> {
}