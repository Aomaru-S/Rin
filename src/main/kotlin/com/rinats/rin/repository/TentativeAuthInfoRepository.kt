package com.rinats.rin.repository

import com.rinats.rin.model.TentativeAuthInfo
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface TentativeAuthInfoRepository : JpaRepository<TentativeAuthInfo, String>