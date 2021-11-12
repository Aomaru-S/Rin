package com.rinats.rin.model.compositeKey

import java.io.Serializable
import java.util.*

data class TentativeShiftKey(
    val date: Date,
    val employeeId: String
) : Serializable