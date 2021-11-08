package com.rinats.rin.model

import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.Id
import javax.persistence.Table

@Entity
@Table(name = "setting")
data class Setting (
    @Id
    @Column(name = "setting_key")
    val settingKey: String,
    @Column(name = "setting_value")
    val settingValue: String,
    @Column(name = "value_type")
    val valueType: String
)