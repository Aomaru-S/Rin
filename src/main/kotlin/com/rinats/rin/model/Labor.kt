package com.rinats.rin.model

import com.rinats.rin.model.compositeKey.LaborId
import javax.persistence.Column
import javax.persistence.EmbeddedId
import javax.persistence.Entity
import javax.persistence.Table

@Entity
@Table(name = "labor")
open class Labor {
    @EmbeddedId
    open var id: LaborId? = null

    @Column(name = "level", nullable = false)
    open var level: Int? = null
}