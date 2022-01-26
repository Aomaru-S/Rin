package com.rinats.rin.model.table.compositeId

import java.io.Serializable
import javax.persistence.Column
import javax.persistence.Embeddable

@Embeddable
open class ShiftTemplateId : Serializable {
    @Column(name = "shift_template_id", nullable = false)
    open var shiftTemplateId: Int? = null

    @Column(name = "role_id", nullable = false)
    open var roleId: Int? = null
}