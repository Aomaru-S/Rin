package com.rinats.rin.model

import org.hibernate.Hibernate
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
        Date()
    )

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other == null || Hibernate.getClass(this) != Hibernate.getClass(other)) return false
        other as Retirement

        return employeeId != null && employeeId == other.employeeId
    }

    override fun hashCode(): Int = javaClass.hashCode()

    @Override
    override fun toString(): String {
        return this::class.simpleName + "(employeeId = $employeeId , firstName = $firstName , lastName = $lastName , gender = $gender , birthday = $birthday , isAndroid = $isAndroid , retireDate = $retireDate )"
    }
}