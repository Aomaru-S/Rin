package com.rinats.rin.model

import org.hibernate.Hibernate
import java.time.LocalDate
import javax.persistence.*
import javax.persistence.Table

@Entity
@Table(name = "employee")
data class Employee(
    @Id
    @Column(name = "employee_id", nullable = false, length = 6)
    var id: String? = null,

    @Column(name = "first_name", nullable = false, length = 32)
    var firstName: String? = null,

    @Column(name = "last_name", nullable = false, length = 32)
    var lastName: String? = null,

    @Column(name = "birthday", nullable = false)
    var birthday: LocalDate? = null,

    @Column(name = "hourly_wage", nullable = false)
    var hourlyWage: Int? = null,

    @Column(name = "is_android_notification", nullable = false)
    var isAndroidNotification: Boolean? = false,

    @Column(name = "mail_address", nullable = false, length = 319)
    var mailAddress: String? = null,

    @Column(name = "is_tentative")
    var isTentative: Boolean? = null,

    @Column(name = "is_taxiable_ok")
    var isTaxableOk: Boolean? = null,

    @ManyToOne(optional = false)
    @JoinColumn(name = "gender_id", nullable = false)
    var gender: Gender? = null
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other == null || Hibernate.getClass(this) != Hibernate.getClass(other)) return false
        other as Employee

        return id != null && id == other.id
    }

    override fun hashCode(): Int = javaClass.hashCode()

    @Override
    override fun toString(): String {
        return this::class.simpleName + "(id = $id , firstName = $firstName , lastName = $lastName , birthday = $birthday , hourlyWage = $hourlyWage , isAndroidNotification = $isAndroidNotification , mailAddress = $mailAddress , isTentative = $isTentative , isTaxableOk = $isTaxableOk , gender = $gender )"
    }
}