package com.rinats.rin.repository;

import com.rinats.rin.model.table.MailAddressAuth
import org.springframework.data.jpa.repository.JpaRepository

interface MailAddressAuthRepository : JpaRepository<MailAddressAuth, String> {
    fun findByUuid(uuid: String): List<MailAddressAuth>
}