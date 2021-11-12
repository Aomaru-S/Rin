package com.rinats.rin.model

import com.rinats.rin.model.compositeKey.StartWorkKey
import java.io.Serializable
import java.sql.Time
import java.util.*
import javax.persistence.*
import javax.persistence.Table

@Entity
@Table(name = "start_work")
@IdClass(value = StartWorkKey::class)
data class StartWork(
    @Id
    @Column(name = "employee_id")
    val employeeId: String,
    @Id
    val date: Date,
    val time: Time
) : Serializable
