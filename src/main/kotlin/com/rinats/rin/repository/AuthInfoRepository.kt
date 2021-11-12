package com.rinats.rin.repository

import com.rinats.rin.model.AuthInfo
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface AuthInfoRepository : JpaRepository<AuthInfo, String>