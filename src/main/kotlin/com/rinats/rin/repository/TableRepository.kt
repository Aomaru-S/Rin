package com.rinats.rin.repository

import com.rinats.rin.model.Table
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface TableRepository : JpaRepository<Table, String>