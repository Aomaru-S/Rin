package com.rinats.rin.repository;

import com.rinats.rin.model.Setting
import com.rinats.rin.model.compositeKey.SettingId
import org.springframework.data.jpa.repository.JpaRepository

interface SettingRepository : JpaRepository<Setting, SettingId>