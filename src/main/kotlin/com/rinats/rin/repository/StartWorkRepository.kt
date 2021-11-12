package com.rinats.rin.repository

import com.rinats.rin.model.StartWork
import com.rinats.rin.model.compositeKey.StartWorkKey
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface StartWorkRepository : JpaRepository<StartWork, StartWorkKey>
