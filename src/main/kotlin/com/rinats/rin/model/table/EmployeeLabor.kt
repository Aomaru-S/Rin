package com.rinats.rin.model.table

import com.rinats.rin.model.table.compositeId.EmployeeLaborId
import javax.persistence.*
import javax.persistence.Table

@Entity
@Table(name = "employee_labor")
open class EmployeeLabor {
    @EmbeddedId
    @Column(name = "employee_id")
    open var id: EmployeeLaborId? = null

    @Column(name = "labor", nullable = false)
    open var labor: Int? = null
}