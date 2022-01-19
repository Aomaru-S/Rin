package com.rinats.rin.model.table

import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.Id
import javax.persistence.Table

@Entity
@Table(name = "authority")
open class Authority {
    @Id
    @Column(name = "authority_id", nullable = false)
    open var id: Int? = null

    @Column(name = "authority_name", nullable = false, length = 32)
    open var authorityName: String? = null
}