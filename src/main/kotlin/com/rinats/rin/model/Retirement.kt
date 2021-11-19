package com.rinats.rin.model

import java.util.*
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.Id
import javax.persistence.Table

@Entity
@Table(name = "employee_retirement")
data class Retirement(
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
    @Column(name = "retire_date")
    val retireDate: Date
) {
    constructor(employee: Employee) : this(
        employee.employeeId,
        employee.firstName,
        employee.lastName,
        employee.gender,
        employee.birthday,
        employee.isAndroid,
        employee.roleId,
        Date()
    )
}