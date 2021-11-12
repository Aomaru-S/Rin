package com.rinats.rin.model

import com.rinats.rin.model.compositeKey.TentativeShiftKey
import java.io.Serializable
import java.util.*
import javax.persistence.*
import javax.persistence.Table

@Entity
@Table(name = "tentative_shift")
@IdClass(value = TentativeShiftKey::class)
data class TentativeShift(
    @Id
    val date: Date,
    @Id
    @Column(name = "employee_id")
    val employeeId: String
) : Serializable
