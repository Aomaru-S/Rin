package com.rinats.rin.repository

import com.rinats.rin.model.AuthInfo
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.util.*

@Repository
interface AuthInfoRepository : JpaRepository<AuthInfo, String> {
    fun existsByAccessToken(accessToken: String): Boolean
    fun findByAccessToken(accessToken: String): Optional<AuthInfo>
}