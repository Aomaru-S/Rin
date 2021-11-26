package com.rinats.rin.model

import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.Id
import javax.persistence.Table

@Table(name = "forget_password_access_token")
@Entity
class ForgetPasswordAccessToken(
    @Id
    @Column(name = "uuid", length = 36)
    val uuid: String? = null,
    @Column(name = "employee_id", nullable = false, length = 6)
    val employeeId: String? = null
)