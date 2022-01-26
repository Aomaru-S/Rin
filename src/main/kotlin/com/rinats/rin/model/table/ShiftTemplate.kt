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

    @Column(name = "weeks_and_holiday_name", nullable = false, length = 32)
    open var weeksAndHolidayName: String? = null

    @Column(name = "num_of_people", nullable = false)
    open var numOfPeople: Int? = null
}