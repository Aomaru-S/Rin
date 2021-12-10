package com.rinats.rin.model.compositeKey

import org.hibernate.Hibernate
import java.io.Serializable
import java.util.*
import javax.persistence.Column
import javax.persistence.Embeddable

@Embeddable
open class LaborId : Serializable {
    @Column(name = "employee_id", nullable = false, length = 6)
    open var employeeId: String? = null

    @Column(name = "role_id", nullable = false, length = 2)
    open var roleId: String? = null

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