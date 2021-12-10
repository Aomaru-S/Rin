package com.rinats.rin.repository;

import com.rinats.rin.model.Labor
import com.rinats.rin.model.compositeKey.LaborId
import org.springframework.data.jpa.repository.JpaRepository

interface LaborRepository : JpaRepository<Labor, LaborId> {
}