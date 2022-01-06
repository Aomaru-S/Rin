package com.rinats.rin.service

import com.rinats.rin.model.Setting
import com.rinats.rin.repository.SettingRepository
import org.springframework.stereotype.Service

@Service
class SetSettingValueInDBService(
    private val settingRepository: SettingRepository
) {
    fun isKeysNull(): Boolean = settingRepository.findAll().isEmpty()

    fun makeKeys() {
        settingRepository.save(Setting("working_hours", "6", Int::class.java.name))
        settingRepository.save(Setting("taxable", "1000000", Int::class.java.name))
    }
}