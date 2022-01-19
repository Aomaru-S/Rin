package com.rinats.rin.repository

import com.rinats.rin.model.table.StartWorkHistory
import com.rinats.rin.model.table.compositeId.StartWorkHistoryKey
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface StartWorkHistoryRepository : JpaRepository<StartWorkHistory, StartWorkHistoryKey>