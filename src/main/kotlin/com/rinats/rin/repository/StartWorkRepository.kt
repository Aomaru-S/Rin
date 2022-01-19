package com.rinats.rin.repository

import com.rinats.rin.model.table.StartWork
import com.rinats.rin.model.table.compositeId.StartWorkKey
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface StartWorkRepository : JpaRepository<StartWork, StartWorkKey>
