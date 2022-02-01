package com.rinats.rin.repository;

import com.rinats.rin.model.table.Settings
import org.springframework.data.jpa.repository.JpaRepository

interface SettingsRepository : JpaRepository<Settings, String> {
}