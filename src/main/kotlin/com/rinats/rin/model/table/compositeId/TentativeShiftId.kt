package com.rinats.rin.model.table.compositeId

import org.hibernate.Hibernate
import java.io.Serializable
import java.time.LocalDate
import java.util.*
import javax.persistence.Column
import javax.persistence.Embeddable

@Embeddable
open class TentativeShiftId(shiftDate: LocalDate, employeeId: String) : Serializable {
    @Column(name = "shift_date", nullable = false)
    open var shiftDate: LocalDate? = shiftDate

    @Column(name = "employee_id", nullable = false, length = 6)
    open var employeeId: String? = employeeId

    override fun hashCode(): Int = Objects.hash(shiftDate, employeeId)
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other == null || Hibernate.getClass(this) != Hibernate.getClass(other)) return false

        other as TentativeShiftId

        return shiftDate == other.shiftDate &&
                employeeId == other.employeeId
    }

    companion object {
        private const val serialVersionUID = 2310591282884924443L
    }
}