package com.rinats.rin.repository

import com.rinats.rin.model.Password
import org.springframework.data.jpa.repository.JpaRepository

interface PasswordRepository : JpaRepository<Password, String>