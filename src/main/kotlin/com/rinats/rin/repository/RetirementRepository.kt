package com.rinats.rin.repository

import com.rinats.rin.model.Retirement
import org.springframework.data.jpa.repository.JpaRepository

interface RetirementRepository : JpaRepository<Retirement, String>