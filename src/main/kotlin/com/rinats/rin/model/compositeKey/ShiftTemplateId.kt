package com.rinats.rin.model.compositeKey

import org.hibernate.Hibernate
import java.io.Serializable
import java.util.*
import javax.persistence.Column
import javax.persistence.Embeddable

@Embeddable
open class ShiftTemplateId : Serializable {
    @Column(name = "day_of_week", nullable = false, length = 9)
    open var dayOfWeek: String? = null

    @Column(name = "role_id", nullable = false, length = 2)
    open var roleId: String? = null

    override fun hashCode(): Int = Objects.hash(dayOfWeek, roleId)
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other == null || Hibernate.getClass(this) != Hibernate.getClass(other)) return false

        other as ShiftTemplateId

        return dayOfWeek == other.dayOfWeek &&
                roleId == other.roleId
    }

    companion object {
        private const val serialVersionUID = 6679930420708318831L
    }
}