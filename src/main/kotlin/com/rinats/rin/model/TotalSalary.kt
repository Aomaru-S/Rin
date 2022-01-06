package com.rinats.rin.model

import com.rinats.rin.model.compositeKey.TotalSalaryId
import javax.persistence.Column
import javax.persistence.EmbeddedId
import javax.persistence.Entity
import javax.persistence.Table

@Entity
@Table(name = "total_salary")
open class TotalSalary {
    @EmbeddedId
    open var id: TotalSalaryId? = null

    @Column(name = "salary")
    open var salary: Int? = null
}