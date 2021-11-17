package com.rinats.rin.model

import java.util.*
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.Id
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
    @Column(name = "is_android_notification")
    val isAndroid: Boolean,
    @Column(name = "role_id")
    val roleId: String,
    @Column(name = "is_tentative")
    val isTentative: Boolean
)