package com.rinats.rin.repository

import com.rinats.rin.model.TentativePassword
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface TentativePasswordRepository : JpaRepository<TentativePassword, String>