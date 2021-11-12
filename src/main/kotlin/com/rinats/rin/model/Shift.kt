package com.rinats.rin.model

import com.rinats.rin.model.compositeKey.ShiftKey
import java.io.Serializable
import java.util.*
import javax.persistence.*
import javax.persistence.Table

@Entity
@Table(name = "shift")
@IdClass(value = ShiftKey::class)
data class Shift(
    @Id
    val date: Date,
    @Id
    @Column(name = "employee_id")
    val employeeId: String
) : Serializable
