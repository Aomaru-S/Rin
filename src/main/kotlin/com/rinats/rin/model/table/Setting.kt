package com.rinats.rin.model.table

import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.Id
import javax.persistence.Table

@Entity
@Table(name = "settings")
open class Setting(setting_key: String, setting_value: String, value_detail: String) {
    @Id
    @Column(name = "setting_key", nullable = false, length = 64)
    open var id: String? = setting_key

    @Column(name = "setting_value", nullable = false, length = 64)
    open var settingValue: String? = setting_value

    @Column(name = "value_detail", nullable = false, length = 64)
    open var valueDetail: String? = value_detail
}