package com.rinats.rin.model

import javax.persistence.*

@Entity
@Table(name = "employee_level")
data class EmployeeLevel(
    @Id
    @Column(name = "employee_id")
    val employeeId: String,
    val level: Int
)