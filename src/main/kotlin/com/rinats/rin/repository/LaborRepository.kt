package com.rinats.rin.repository

import com.rinats.rin.model.Labor
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface LaborRepository : JpaRepository<Labor, String>