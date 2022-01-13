package com.rinats.rin.model

import com.rinats.rin.model.compositeKey.TentativeShiftId
import javax.persistence.*
import javax.persistence.Table

@Entity
@Table(name = "tentative_shift")
open class TentativeShift(saveShiftId: TentativeShiftId, role: Role) {
    @EmbeddedId
    open var id: TentativeShiftId? = saveShiftId

    @ManyToOne(optional = false)
    @JoinColumn(name = "role_id", nullable = false)
    open var role: Role? = role
}