package com.rinats.rin.model.form

import java.io.Serializable
import javax.validation.Valid

data class ShiftTemplateFormList(
    @Valid
    var shiftTemplateList: List<ShiftTemplateForm> = mutableListOf()
) : Serializable