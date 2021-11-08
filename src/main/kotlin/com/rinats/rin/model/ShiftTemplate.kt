package com.rinats.rin.model

import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.Id
import javax.persistence.Table

@Entity
@Table(name = "shift_template")
data class ShiftTemplate(
    @Id
    @Column(name = "day_of_week")
    val dayOfWeek: String,
    @Column(name = "num_of_people")
    val numOfPeople: Int
)
