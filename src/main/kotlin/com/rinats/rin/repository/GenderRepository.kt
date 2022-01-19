package com.rinats.rin.repository;

import com.rinats.rin.model.table.Gender
import org.springframework.data.jpa.repository.JpaRepository

interface GenderRepository : JpaRepository<Gender, Int> {
}