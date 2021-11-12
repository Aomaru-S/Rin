package com.rinats.rin.model.compositeKey

import java.io.Serializable
import java.util.*

data class ShiftKey(
    val date: Date,
    val employeeId: String
) : Serializable
