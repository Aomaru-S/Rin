package com.rinats.rin.model.form

import java.io.Serializable
import javax.validation.constraints.Min
import javax.validation.constraints.NotNull

data class ShiftTemplateForm(
    @field:NotNull
    @field:Min(0)
    var role: Int? = null,
    @field:Min(0)
    var monday: Int = 0,
    @field:Min(0)
    var tuesday: Int = 0,
    @field:Min(0)
    var wednesday: Int = 0,
    @field:Min(0)
    var thursday: Int = 0,
    @field:Min(0)
    var friday: Int = 0,
    @field:Min(0)
    var saturday: Int = 0,
    @field:Min(0)
    var sunday: Int = 0,
    @field:Min(0)
    var holiday: Int = 0
) : Serializable