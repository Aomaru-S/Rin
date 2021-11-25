package com.rinats.rin.model

import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.Id
import javax.persistence.Table

@Table(name = "forget_password_access_token")
@Entity
class ForgetPasswordAccessToken {
    @Id
    @Column(name = "employee_id", nullable = false, length = 6)
    var id: String? = null

    @Column(name = "uuid", length = 36)
    var uuid: String? = null
}