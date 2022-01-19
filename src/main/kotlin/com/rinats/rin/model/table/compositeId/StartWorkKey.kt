package com.rinats.rin.model.table.compositeId

import java.io.Serializable
import java.util.*

data class StartWorkKey(
    val employeeId: String,
    val date: Date
) : Serializable
