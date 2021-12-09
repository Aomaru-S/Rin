package com.rinats.rin.model

import javax.persistence.*
import javax.persistence.Table

@Entity
@Table(name = "labor")
open class Labor {
    @EmbeddedId
    open var id: LaborId? = null

    @Column(name = "level", nullable = false)
    open var level: Int? = null
}