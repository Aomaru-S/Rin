package com.rinats.rin.model

import org.hibernate.Hibernate
import java.io.Serializable
import java.time.LocalDate
import java.util.*
import javax.persistence.Column
import javax.persistence.Embeddable
import javax.persistence.Entity

@Embeddable
open class TentativeShiftId : Serializable {
    @Column(name = "date", nullable = false)
    open var date: LocalDate? = null

    @Column(name = "employee_id", nullable = false, length = 6)
    open var employeeId: String? = null

    override fun hashCode(): Int = Objects.hash(date, employeeId)
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other == null || Hibernate.getClass(this) != Hibernate.getClass(other)) return false

        other as TentativeShiftId

        return date == other.date &&
                employeeId == other.employeeId
    }

    companion object {
        private const val serialVersionUID = -4616517438659656074L
    }
}