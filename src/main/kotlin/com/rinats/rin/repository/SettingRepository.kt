package com.rinats.rin.repository

import com.rinats.rin.model.Setting
import org.springframework.data.jpa.repository.JpaRepository

interface SettingRepository : JpaRepository<Setting, String>