package com.rinats.rin.model.table

import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.Id
import javax.persistence.Table

@Entity
@Table(name = "settings")
open class Settings {
    @Id
    @Column(name = "setting_key", nullable = false, length = 64)
    open var id: String? = null

    @Column(name = "setting_value", nullable = false, length = 64)
    open var settingValue: String? = null

    @Column(name = "value_detail", nullable = false, length = 64)
    open var valueDetail: String? = null
}