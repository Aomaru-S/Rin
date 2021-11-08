package com.rinats.rin.model

import java.io.Serializable
import java.util.*
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.Id
import javax.persistence.Table

@Entity
@Table(name = "end_work_history")
data class EndWorkHistory(
    @Id
    @Column(name = "employee_id")
    val employeeId: String,
    @Id
    @Column(name = "start_date")
    val startDate: Date,
    @Id
    @Column(name = "change_count")
    val changeCount: Int,
    @Column(name = "before_date")
    val beforeDate: Date,
    @Column(name = "edit_employee_id")
    val editEmployeeId: String
) : Serializable
