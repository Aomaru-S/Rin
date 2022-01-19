package com.rinats.rin.model.table

import com.rinats.rin.model.table.compositeId.ShiftHopeId
import javax.persistence.EmbeddedId
import javax.persistence.Entity
import javax.persistence.Table

@Entity
@Table(name = "shift_hope")
open class ShiftHope {
    @EmbeddedId
    open var id: ShiftHopeId? = null
}
