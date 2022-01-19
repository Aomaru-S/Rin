package com.rinats.rin.repository

import com.rinats.rin.model.table.SequenceNumber
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface SequenceNumberRepository : JpaRepository<SequenceNumber, String>