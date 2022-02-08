package com.rinats.rin.model.table.compositeId

import com.rinats.rin.model.table.Employee
import org.hibernate.Hibernate
import java.io.Serializable
import java.time.LocalDate
import java.util.*
import javax.persistence.Column
import javax.persistence.Embeddable
import javax.persistence.JoinColumn
import javax.persistence.ManyToOne

@Embeddable
open class TentativeShiftId(shiftDate: LocalDate, employee: Employee) : Serializable {
    @Column(name = "date", nullable = false)
    open var shiftDate: LocalDate? = shiftDate

    @ManyToOne(optional = false)
    @JoinColumn(name = "employee_id", nullable = false)
    open var employee: Employee? = employee
}