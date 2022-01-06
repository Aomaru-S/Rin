package com.rinats.rin.model

import com.rinats.rin.model.compositeKey.EmployeeLaborId
import javax.persistence.Column
import javax.persistence.EmbeddedId
import javax.persistence.Entity
import javax.persistence.Table

@Entity
@Table(name = "employee_labor")
open class EmployeeLabor {

    @EmbeddedId
    open var id: EmployeeLaborId? = null

    @Column(name = "labor", nullable = false)
    open var labor: Int? = null
}