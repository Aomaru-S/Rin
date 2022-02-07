package com.rinats.rin.model.table

import java.time.LocalDate
import javax.persistence.*
import javax.persistence.Table

@Entity
@Table(name = "employee")
open class Employee {
    @Id
    @Column(name = "employee_id", nullable = false, length = 6)
    open var id: String? = null

    @Column(name = "first_name", nullable = false, length = 32)
    open var firstName: String? = null

    @Column(name = "last_name", nullable = false, length = 32)
    open var lastName: String? = null

    @Column(name = "birthday", nullable = false)
    open var birthday: LocalDate? = null

    @Column(name = "hourly_wage", nullable = false)
    open var hourlyWage: Int? = null

    @Column(name = "is_android_notification", nullable = false)
    open var isAndroidNotification: Boolean? = false

    @Column(name = "mail_address", nullable = false, length = 319)
    open var mailAddress: String? = null

    @Column(name = "is_tentative")
    open var isTentative: Boolean? = null

    @Column(name = "is_retirement", nullable = false)
    open var isRetirement: Boolean? = false

    @ManyToOne(optional = false)
    @JoinColumn(name = "gender_id", nullable = false)
    open var gender: Gender? = null

    @Column(name = "is_taxable_ok")
    open var isTaxableOk: Boolean? = null
}