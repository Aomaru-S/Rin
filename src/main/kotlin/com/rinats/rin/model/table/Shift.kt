package com.rinats.rin.model.table

import com.rinats.rin.model.table.compositeId.ShiftKey
import java.io.Serializable
import java.util.*
import javax.persistence.*
import javax.persistence.Table

@Entity
@Table(name = "shift")
@IdClass(value = ShiftKey::class)
data class Shift(
    @Id
    var date: Date,
    @Id
    @Column(name = "employee_id")
    var employeeId: String
) : Serializable
