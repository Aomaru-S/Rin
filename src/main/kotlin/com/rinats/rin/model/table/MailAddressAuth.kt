package com.rinats.rin.model.table

import java.time.Instant
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.Id
import javax.persistence.Table

@Entity
@Table(name = "mail_address_auth")
open class MailAddressAuth {
    @Id
    @Column(name = "employee_id", nullable = false, length = 6)
    open var id: String? = null

    @Column(name = "mail_address", nullable = false, length = 319)
    open var mailAddress: String? = null

    @Column(name = "uuid", nullable = false, length = 36)
    open var uuid: String? = null

    @Column(name = "expire", nullable = false)
    open var expire: Instant? = null
}