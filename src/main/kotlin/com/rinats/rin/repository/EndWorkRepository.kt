package com.rinats.rin.repository

import com.rinats.rin.model.EndWork
import com.rinats.rin.model.compositeKey.EndWorkKey
import org.springframework.data.jpa.repository.JpaRepository

interface EndWorkRepository : JpaRepository<EndWork,EndWorkKey>