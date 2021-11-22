package com.rinats.rin.model.compositeKey

import java.io.Serializable
import java.sql.*

data class ShiftHopeKey(
    var date: Date,
    var employeeId: String
) : Serializable {
    constructor() : this(Date(0), "") {
    }
}