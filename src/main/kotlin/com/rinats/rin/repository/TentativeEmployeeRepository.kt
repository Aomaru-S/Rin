package com.rinats.rin.repository

import com.rinats.rin.model.TentativeEmployee
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface TentativeEmployeeRepository : JpaRepository<TentativeEmployee, String>