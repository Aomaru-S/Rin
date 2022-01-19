package com.rinats.rin.model.table

import java.util.*
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.Id
import javax.persistence.Table

@Entity
@Table(name = "tentative_shift_history")
data class TentativeShiftHistory(
    @Id
    val id: Int,
    val date: Date,
    @Column(name = "employee_id")
    val employeeId: String
)
