package com.rinats.rin.service

import com.rinats.rin.model.form.ShiftTemplateForm
import com.rinats.rin.model.form.ShiftTemplateFormList
import com.rinats.rin.model.table.compositeId.ShiftTemplateId
import com.rinats.rin.repository.ShiftTemplateRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class ShiftTemplateService(
    @Autowired
    private val templateRepository: ShiftTemplateRepository
) {
    fun saveShiftTemplate(shiftTemplateFormList: ShiftTemplateFormList): Boolean {
        val shiftTemplateId = ShiftTemplateId().also {
//            it.
        }

        return true
    }
}