package com.rinats.rin.repository

import com.rinats.rin.model.table.TentativeShift
import com.rinats.rin.model.table.compositeId.TentativeShiftId
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface TentativeShiftRepository : JpaRepository<TentativeShift, TentativeShiftId>