package com.rinats.rin.model

import javax.persistence.Column
import javax.persistence.EmbeddedId
import javax.persistence.Entity
import javax.persistence.Table

@Entity
@Table(name = "tentative_shift2")
open class TentativeShift {
    @EmbeddedId
    open var id: TentativeShiftId? = null

    @Column(name = "role_id", nullable = false)
    open var roleId: Int? = null
}