package com.rinats.rin.model.compositeKey

import org.hibernate.Hibernate
import java.io.Serializable
import java.sql.*
import java.time.LocalDate
import java.util.*
import javax.persistence.Column
import javax.persistence.Embeddable

@Embeddable
open class ShiftHopeId : Serializable {
    @Column(name = "date", nullable = false)
    open var shiftDate: LocalDate? = null

    @Column(name = "employee_id", nullable = false, length = 6)
    open var employeeId: String? = null

    override fun hashCode(): Int = Objects.hash(shiftDate, employeeId)
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other == null || Hibernate.getClass(this) != Hibernate.getClass(other)) return false

        other as ShiftHopeId

        return shiftDate == other.shiftDate &&
                employeeId == other.employeeId
    }

    companion object {
        private const val serialVersionUID = -6477242228297496544L
    }
}