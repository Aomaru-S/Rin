package com.rinats.rin.model

import java.sql.Time
import java.util.*
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.Id
import javax.persistence.Table

@Entity
@Table(name = "work_end")
data class EndWork(
    @Id
    @Column(name = "employee_id")
    val employeeId: String,
    @Id
    @Column(name = "start_date")
    val startDate: Date,
    @Id
    @Column(name = "time")
    val time: Time
)
