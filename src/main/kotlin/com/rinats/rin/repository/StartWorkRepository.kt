package com.rinats.rin.repository

import com.rinats.rin.model.compositeKey.StartWorkKey
import org.springframework.data.jpa.repository.JpaRepository

interface StartWorkRepository : JpaRepository<StartWorkRepository, StartWorkKey>
