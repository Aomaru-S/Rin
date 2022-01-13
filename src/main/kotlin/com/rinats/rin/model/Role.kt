package com.rinats.rin.model

import javax.persistence.*
import javax.persistence.Table

@Entity
@Table(name = "role")
open class Role {
    @Id
    @Column(name = "role_id", nullable = false)
    open var id: Int? = null

    @Column(name = "role_name", nullable = false, length = 32)
    open var roleName: String? = null

    @ManyToOne(optional = false)
    @JoinColumn(name = "authority_id", nullable = false)
    open var authority: Authority? = null
}