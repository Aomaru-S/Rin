package com.rinats.rin.model.table.compositeId

import org.hibernate.Hibernate
import java.io.Serializable
import java.util.*
import javax.persistence.Column
import javax.persistence.Embeddable

@Embeddable
open class TotalSalaryId : Serializable {
    @Column(name = "year", nullable = false)
    open var year: Int? = null

    @Column(name = "employee_id", nullable = false, length = 6)
    open var employeeId: String? = null

    override fun hashCode(): Int = Objects.hash(year, employeeId)
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other == null || Hibernate.getClass(this) != Hibernate.getClass(other)) return false

        other as TotalSalaryId

        return year == other.year &&
                employeeId == other.employeeId
    }

    companion object {
        private const val serialVersionUID = 2391084447852647444L
    }
}