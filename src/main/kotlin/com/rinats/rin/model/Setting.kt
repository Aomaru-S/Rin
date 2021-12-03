package com.rinats.rin.model

import com.rinats.rin.model.compositeKey.SettingId
import javax.persistence.Column
import javax.persistence.EmbeddedId
import javax.persistence.Entity
import javax.persistence.Table

@Table(name = "setting")
@Entity
open class Setting {
    @EmbeddedId
    open var id: SettingId? = null

    @Column(name = "setting_value", length = 128)
    open var settingValue: String? = null
}