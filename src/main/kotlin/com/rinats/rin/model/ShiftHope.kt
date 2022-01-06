package com.rinats.rin.model

import com.rinats.rin.model.compositeKey.ShiftHopeId
import javax.persistence.EmbeddedId
import javax.persistence.Entity
import javax.persistence.Table

@Entity
@Table(name = "shift_hope")
open class ShiftHope {
    @EmbeddedId
    open var id: ShiftHopeId? = null
}
