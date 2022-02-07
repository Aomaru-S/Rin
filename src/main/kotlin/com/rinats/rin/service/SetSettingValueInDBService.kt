package com.rinats.rin.service

import com.rinats.rin.model.table.Settings
import com.rinats.rin.repository.SettingsRepository
import org.springframework.stereotype.Service

@Service
class SetSettingValueInDBService(
    private val settingsRepository: SettingsRepository
) {
    fun isKeysNull(): Boolean = settingsRepository.findAll().isEmpty()

    fun makeKeys() {

        settingsRepository.save(Settings().also {
            it.id = "working_hours"
            it.settingValue = "6"
            it.valueDetail = Int::class.java.name
        })

        settingsRepository.save(Settings().also {
            it.id = "taxable"
            it.settingValue = "1000000"
            it.valueDetail = Int::class.java.name
        })
    }
}