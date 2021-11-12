package com.rinats.rin.repository

import com.rinats.rin.model.Password
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface PasswordRepository : JpaRepository<Password, String>