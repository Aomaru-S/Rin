package com.rinats.rin.model

import org.hibernate.Hibernate
import java.io.Serializable
import java.util.*
import javax.persistence.Column
import javax.persistence.Embeddable
import javax.persistence.Entity

@Embeddable
class LaborId() : Serializable {
    @Column(name = "employee_id", nullable = false, length = 6)
    var employeeId: String? = null

    @Column(name = "role_id", nullable = false, length = 2)
    var roleId: String? = null

    constructor(
        employeeId: String,
        roleId: String
    ) : this() {
        this.employeeId = employeeId
        this.roleId = roleId
    }

    override fun hashCode(): Int = Objects.hash(employeeId, roleId)
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other == null || Hibernate.getClass(this) != Hibernate.getClass(other)) return false

        other as LaborId

        return employeeId == other.employeeId &&
                roleId == other.roleId
    }

    companion object {
        private const val serialVersionUID = 7950450731975342367L
    }
}