package com.rinats.rin.model.table.compositeId

import com.rinats.rin.model.table.Employee
import com.rinats.rin.model.table.Gender
import org.hibernate.Hibernate
import java.io.Serializable
import java.time.LocalDate
import java.util.*
import javax.persistence.Column
import javax.persistence.Embeddable
import javax.persistence.JoinColumn
import javax.persistence.ManyToOne

@Embeddable
open class ShiftHopeId : Serializable {
    @Column(name = "date", nullable = false)
    open var shiftDate: LocalDate? = null

//    @Column(name = "employee_id", nullable = false, length = 6)
//    open var employeeId: String? = null

    @ManyToOne(optional = false)
    @JoinColumn(name = "employee_id", nullable = false)
    open var employee: Employee? = null
}