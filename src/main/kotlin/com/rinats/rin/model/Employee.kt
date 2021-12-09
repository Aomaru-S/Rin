package com.rinats.rin.model

import org.hibernate.Hibernate
import org.mybatis.dynamic.sql.util.kotlin.elements.concatenate
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
    var hourlyWage: Int,
    @Column(name = "is_android_notification")
    val isAndroid: Boolean,
    var mailAddress: String,
    @Column(name = "role_id")
    val roleId: String,
    @OneToMany(cascade = [CascadeType.ALL], fetch = FetchType.EAGER)
    @JoinColumn(name = "employee_id")
    var laborList: MutableList<Labor>
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other == null || Hibernate.getClass(this) != Hibernate.getClass(other)) return false
        other as Employee

        return employeeId == other.employeeId
    }

    override fun hashCode(): Int = javaClass.hashCode()
    override fun toString(): String {
        return "Employee(employeeId='$employeeId', firstName='$firstName', lastName='$lastName', gender=$gender, birthday=$birthday, hourlyWage=$hourlyWage, isAndroid=$isAndroid, mailAddress='$mailAddress', laborList=$laborList)"
    }

    fun hasRole(_roleId: String): Boolean {
        val roleIdList = mutableListOf<String>()
        laborList.forEach {
            val roleId = it.id?.roleId ?: return@forEach
            roleIdList.add(roleId)
        }
        return roleIdList.contains(_roleId)
    }
}
