package com.rinats.rin.model

import java.util.*
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.Id
import javax.persistence.Table

@Entity
@Table(name = "password")
data class Password(
    @Id
    @Column(name = "employee_id")
    val employeeId: String,
    val password: String,
    val salt: String,
    @Column(name = "is_lockout")
    val isLockout: Boolean,
    @Column(name = "access_token")
    val accessToken: String,
    val expire: Date
)
