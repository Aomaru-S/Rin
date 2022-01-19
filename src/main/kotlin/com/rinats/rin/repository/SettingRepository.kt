package com.rinats.rin.repository;

import com.rinats.rin.model.table.Setting
import com.rinats.rin.model.table.compositeId.SettingId
import org.springframework.data.jpa.repository.JpaRepository

interface SettingRepository : JpaRepository<Setting, SettingId>