package com.rinats.rin.model.table

import com.rinats.rin.model.table.compositeId.ShiftTemplateId
import javax.persistence.Column
import javax.persistence.EmbeddedId
import javax.persistence.Entity
import javax.persistence.Table

@Entity
@Table(name = "shift_template")
open class ShiftTemplate {
    @EmbeddedId
    open var id: ShiftTemplateId? = null

    @Column(name = "num_of_people")
    open var numOfPeople: Int? = null
}