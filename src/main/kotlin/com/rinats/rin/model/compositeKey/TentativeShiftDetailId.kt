package com.alias.kh.shiftgenerator.model.compositeKey

import org.hibernate.Hibernate
import java.io.Serializable
import java.time.LocalDate
import java.util.*
import javax.persistence.Column
import javax.persistence.Embeddable

@Embeddable
open class TentativeShiftDetailId(shiftDate: LocalDate, roleId: Int) : Serializable {
    @Column(name = "shift_date", nullable = false)
    open var shiftDate: LocalDate? = shiftDate

    @Column(name = "role_id", nullable = false)
    open var roleId: Int? = roleId

    override fun hashCode(): Int = Objects.hash(shiftDate, roleId)
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other == null || Hibernate.getClass(this) != Hibernate.getClass(other)) return false

        other as TentativeShiftDetailId

        return shiftDate == other.shiftDate &&
                roleId == other.roleId
    }

    companion object {
        private const val serialVersionUID = -4461771692166539445L
    }
}