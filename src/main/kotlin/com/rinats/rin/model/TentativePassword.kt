package com.rinats.rin.model

import java.time.LocalDateTime
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.Id
import javax.persistence.Table

@Entity
@Table(name = "tentative_password")
data class TentativePassword (
        @Id
        @Column(name = "employee_id")
        val employeeId: String,
        val password: String,
        val salt: String,
        @Column(name = "is_lockout")
        val isLockOut: Boolean,
        @Column(name = "access_token")
        val accessToken: String,
        val expire: LocalDateTime
)

