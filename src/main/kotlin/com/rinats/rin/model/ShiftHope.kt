package com.rinats.rin.model

import com.rinats.rin.model.compositeKey.ShiftHopeKey
import java.io.Serializable
import java.sql.*
import javax.persistence.*
import javax.persistence.Table

@Entity
@Table(name = "shift_hope")
@IdClass(value = ShiftHopeKey::class)
data class ShiftHope(
    @Id
    val date: Date,
    @Id
    @Column(name = "employee_id")
    val employeeId: String,
) : Serializable
