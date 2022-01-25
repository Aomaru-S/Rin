package com.rinats.rin.model.table

import javax.persistence.*
import javax.persistence.Table

@Entity
@Table(name = "shift_template")
open class ShiftTemplate {
    @Id
    @Column(name = "shift_template_id", nullable = false)
    open var id: Int? = null

    @ManyToOne(optional = false)
    @JoinColumn(name = "role_id", nullable = false)
    open var role: Role? = null

    @Column(name = "weeks_and_holiday_name", nullable = false, length = 32)
    open var weeksAndHolidayName: String? = null

    @Column(name = "num_of_people", nullable = false)
    open var numOfPeople: Int? = null
}