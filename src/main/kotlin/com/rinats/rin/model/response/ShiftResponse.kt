package com.rinats.rin.model.response

import com.rinats.rin.service.ShiftService

data class ShiftResponse(
    val year: Int,
    val month: Int,
    val days: List<ShiftService.ShiftDay>
)
