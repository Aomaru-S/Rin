package com.rinats.rin.repository

import com.rinats.rin.model.ShiftSetting
import org.springframework.data.jpa.repository.JpaRepository
import java.util.*

interface ShiftSettingRepository : JpaRepository<ShiftSetting, Date>