package com.rinats.rin.model

import java.util.*
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.Id
import javax.persistence.Table

@Entity
@Table(name = "auth_info")
data class AuthInfo(
    @Id
    @Column(name = "employee_id", nullable = false)
    val employeeId: String,
    var password: String,
    var salt: String,
    @Column(name = "is_lockout")
    val isLockout: Boolean,
    @Column(name = "access_token")
    var accessToken: String,
    var expire: Date
)
