package com.rinats.rin.model.table

import com.rinats.rin.model.table.compositeId.EndWorkKey

import java.io.Serializable
import java.sql.Time
import java.util.*
import javax.persistence.*
import javax.persistence.Table

@Entity
@Table(name = "end_work")
@IdClass(value = EndWorkKey::class)
data class EndWork (
    @Id
    @Column(name = "employee_id")
    val employeeId: String,
    @Id
    @Column(name = "start_date")
    val startDate: Date,
    @Id
    @Column(name = "time")
    val time: Time
) : Serializable