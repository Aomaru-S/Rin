package com.rinats.rin.model.compositeKey

import org.hibernate.Hibernate
import java.io.Serializable
import java.util.*
import javax.persistence.Embeddable

@Embeddable
open class EmployeeLaborId(
    open var employeeId: String? = null,

    open var roleId: Int? = null

) : Serializable {


    override fun hashCode(): Int = Objects.hash(employeeId, roleId)
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other == null || Hibernate.getClass(this) != Hibernate.getClass(other)) return false

        other as EmployeeLaborId

        return employeeId == other.employeeId &&
                roleId == other.roleId
    }

    companion object {
        private const val serialVersionUID = 5828861879695785116L
    }
}