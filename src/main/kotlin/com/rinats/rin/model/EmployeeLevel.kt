package com.rinats.rin.model

import java.io.Serializable
import javax.persistence.*
import javax.persistence.Table

@Entity
@Table(name = "employee_level")

data class EmployeeLevel(
    @Id
    @JoinColumn(name = "employee_id")
    @OneToOne(cascade = [CascadeType.ALL],fetch = FetchType.LAZY)
    val employeeId: Employee,
    val level: Int
) : Serializable