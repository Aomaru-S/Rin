package com.rinats.rin.repository

import com.rinats.rin.model.StartWorkHistory
import com.rinats.rin.model.compositeKey.StartWorkHistoryKey
import org.springframework.data.jpa.repository.JpaRepository

interface StartWorkHistoryRepository : JpaRepository<StartWorkHistory, StartWorkHistoryKey>