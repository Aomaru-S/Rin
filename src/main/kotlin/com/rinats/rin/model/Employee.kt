package com.rinats.rin.model

import java.util.*
import javax.persistence.*

@Entity
@Table(name = "Employee")
data class Employee(
    @Id
    @Column(name = "employee_id")
    val employeeId: String,
    @Column(name = "first_name")
    val firstName: String,
    @Column(name = "last_name")
    val lastName: String,
    val gender: Boolean,
    val birthday: Date,
    @Column(name = "is_android_notification")
    val isAndroid: Boolean,
    @Column(name = "role_id")
    val roleId: String
)