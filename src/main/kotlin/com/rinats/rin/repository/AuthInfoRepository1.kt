package com.rinats.rin.repository;

import com.rinats.rin.model.AuthInfo
import org.springframework.data.jpa.repository.JpaRepository

interface AuthInfoRepository1 : JpaRepository<AuthInfo, String> {
}