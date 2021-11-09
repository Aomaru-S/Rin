package com.rinats.rin.repository

import com.rinats.rin.model.EmployeeLevel
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface EmployeeLevel : JpaRepository<EmployeeLevel, String>