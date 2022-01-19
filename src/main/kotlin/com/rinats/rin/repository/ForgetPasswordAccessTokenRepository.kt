package com.rinats.rin.repository;

import com.rinats.rin.model.table.ForgetPasswordAccessToken
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface ForgetPasswordAccessTokenRepository : JpaRepository<ForgetPasswordAccessToken, String>