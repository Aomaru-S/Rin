package com.rinats.rin.model.table.compositeId

import org.hibernate.Hibernate
import java.io.Serializable
import java.util.*
import javax.persistence.Column
import javax.persistence.Embeddable

@Embeddable
open class ShiftTemplateId : Serializable {
    @Column(name = "shift_template_id", nullable = false)
    open var shiftTemplateId: Int? = null

    @Column(name = "role_id", nullable = false)
    open var roleId: Int? = null

    override fun hashCode(): Int = Objects.hash(shiftTemplateId, roleId)
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other == null || Hibernate.getClass(this) != Hibernate.getClass(other)) return false

        other as ShiftTemplateId

        return shiftTemplateId == other.shiftTemplateId &&
                roleId == other.roleId
    }

    companion object {
        private const val serialVersionUID = 6686470367746580769L
    }
}