package com.rinats.rin.model.table.compositeId

import com.rinats.rin.model.enum.SettingKeys
import org.hibernate.Hibernate
import java.io.Serializable
import java.util.*
import javax.persistence.Column
import javax.persistence.Embeddable
import javax.persistence.EnumType
import javax.persistence.Enumerated

@Embeddable
open class SettingId : Serializable {
    @Column(name = "employee_id", nullable = false, length = 6)
    open var employeeId: String? = null

    @Enumerated(EnumType.STRING)
    @Column(name = "setting_key", nullable = false, length = 16)
    open var settingKey: SettingKeys? = null

    override fun hashCode(): Int = Objects.hash(employeeId, settingKey)
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other == null || Hibernate.getClass(this) != Hibernate.getClass(other)) return false

        other as SettingId

        return employeeId == other.employeeId &&
                settingKey == other.settingKey
    }

    companion object {
        private const val serialVersionUID = 1407367662466404787L
    }
}