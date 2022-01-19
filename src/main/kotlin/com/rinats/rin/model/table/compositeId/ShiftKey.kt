package com.rinats.rin.model.table.compositeId

import java.io.Serializable
import java.util.*

data class ShiftKey(
    val date: Date,
    val employeeId: String
) : Serializable {
    constructor() : this(Date(0), "")
}
