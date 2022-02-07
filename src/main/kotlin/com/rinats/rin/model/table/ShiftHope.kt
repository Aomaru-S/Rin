package com.rinats.rin.model.table

import com.rinats.rin.model.table.compositeId.ShiftHopeId
import java.io.Serializable
import javax.persistence.*
import javax.persistence.Table

@Entity
@Table(name = "shift_hope")
open class ShiftHope : Serializable {
    @EmbeddedId
    open var id: ShiftHopeId? = null
}
