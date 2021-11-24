package com.rinats.rin.repository

import com.rinats.rin.model.Employee
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.util.*

@Repository
interface EmployeeRepository : JpaRepository<Employee, String> {
    fun findByMailAddress(mailAddress: String): Optional<Employee>
}