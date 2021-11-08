package com.rinats.rin.model

import java.util.*
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.Id
import javax.persistence.Table

@Entity
@Table(name = "shift_setting")
data class ShiftSetting(
    @Id
    val date: Date,
    @Column(name = "num_of_people")
    val numOfPeople: Int
)
