package com.rinats.rin.model.table.compositeId

import org.hibernate.Hibernate
import java.io.Serializable
import java.util.*
import javax.persistence.Column
import javax.persistence.Embeddable

@Embeddable
open class ShiftTemplateId : Serializable {
    @Column(name = "role_id", nullable = false)
    open var roleId: Int? = null

    @Column(name = "weeks_holiday_name", nullable = false, length = 32)
    open var weeksHolidayName: String? = null

    override fun hashCode(): Int = Objects.hash(roleId, weeksHolidayName)
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other == null || Hibernate.getClass(this) != Hibernate.getClass(other)) return false

        other as ShiftTemplateId

        return roleId == other.roleId &&
                weeksHolidayName == other.weeksHolidayName
    }

    companion object {
        private const val serialVersionUID = 8245852515967239809L
    }
}