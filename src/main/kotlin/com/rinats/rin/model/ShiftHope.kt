package com.rinats.rin.model

import java.io.Serializable
import java.util.*
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.Id
import javax.persistence.Table

@Entity
@Table(name = "shift_hope")
data class ShiftHope(
    @Id
    val date: Date,
    @Id
    @Column(name = "employee_id")
    val employeeId: String,
) : Serializable
