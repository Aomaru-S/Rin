package com.rinats.rin.model

import java.util.*
import javax.persistence.*
import javax.persistence.Table

@Entity
@Table(name = "employee")
data class Employee(
    @Id
    @Column(name = "employee_id")
    val employeeId: String,
    @Column(name = "first_name")
    val firstName: String,
    @Column(name = "last_name")
    val lastName: String,
    val gender: Boolean?,
    val birthday: Date,
    @Column(name = "hourly_wage")
    val hourlyWage: Int,
    @Column(name = "is_android_notification")
    val isAndroid: Boolean,
    @Column(name = "role_id")
    var roleId: String
)