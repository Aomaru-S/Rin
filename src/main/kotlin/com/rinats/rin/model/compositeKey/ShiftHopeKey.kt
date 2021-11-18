package com.rinats.rin.model.compositeKey

import java.io.Serializable
import java.sql.*

data class ShiftHopeKey(
    val date: Date,
    val employeeId: String
) : Serializable