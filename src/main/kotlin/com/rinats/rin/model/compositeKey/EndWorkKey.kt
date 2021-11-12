package com.rinats.rin.model.compositeKey

import java.io.Serializable
import java.sql.Time
import java.util.*


data class EndWorkKey(
    val employeeId: String,
    val startDate: Date,
    val time: Time
) : Serializable