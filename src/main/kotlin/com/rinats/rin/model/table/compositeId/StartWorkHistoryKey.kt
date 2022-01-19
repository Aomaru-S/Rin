package com.rinats.rin.model.table.compositeId

import java.io.Serializable
import java.util.*

data class StartWorkHistoryKey(
    val employeeId: String,
    val date: Date,
    val changeCount: Int
) : Serializable
