package com.rinats.rin.model

import java.io.Serializable
import java.util.*
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.Id
import javax.persistence.Table

@Entity
@Table(name = "start_work_history")
data class StartWorkHistory(
    @Id
    @Column(name = "employee_id")
    val employeeId: String,
    @Id
    val date: Date,
    @Id
    @Column(name = "change_count")
    val changeCount: Int,
    @Column(name = "edit_employee_id")
    val editEmployeeId: String,
) : Serializable
