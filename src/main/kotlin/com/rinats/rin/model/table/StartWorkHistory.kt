package com.rinats.rin.model.table

import com.rinats.rin.model.table.compositeId.StartWorkHistoryKey
import java.io.Serializable
import java.util.*
import javax.persistence.*
import javax.persistence.Table

@Entity
@Table(name = "start_work_history")
@IdClass(value = StartWorkHistoryKey::class)
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
