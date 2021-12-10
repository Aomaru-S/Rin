package com.rinats.rin.model

import java.time.LocalDate
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.Id
import javax.persistence.Table

@Entity
@Table(name = "tentative_shift_detail")
open class TentativeShiftDetail {
    @Id
    @Column(name = "date", nullable = false)
    open var id: LocalDate? = null

    @Column(name = "is_employee_insufficient")
    open var isEmployeeInsufficient: Boolean? = null

    @Column(name = "is_labor_insufficient")
    open var isLaborInsufficient: Boolean? = null
}