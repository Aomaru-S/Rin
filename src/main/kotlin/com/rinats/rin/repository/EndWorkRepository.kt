package com.rinats.rin.repository

import com.rinats.rin.model.table.EndWork
import com.rinats.rin.model.table.compositeId.EndWorkKey
import org.springframework.data.jpa.repository.JpaRepository

interface EndWorkRepository : JpaRepository<EndWork,EndWorkKey>