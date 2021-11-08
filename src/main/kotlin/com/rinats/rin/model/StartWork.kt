package com.rinats.rin.model

import java.io.Serializable
import java.sql.Time
import java.util.*
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.Id
import javax.persistence.Table

@Entity
@Table(name = "start_work")
data class StartWork(
    @Id
    @Column(name = "employee_id")
    val employee: String,
    @Id
    val date: Date,
    val time: Time
) : Serializable
