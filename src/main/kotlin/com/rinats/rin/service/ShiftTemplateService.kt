package com.rinats.rin.service

import com.rinats.rin.model.form.ShiftTemplateForm
import com.rinats.rin.model.form.ShiftTemplateFormList
import com.rinats.rin.model.table.ShiftTemplate
import com.rinats.rin.model.table.compositeId.ShiftTemplateId
import com.rinats.rin.repository.RoleRepository
import com.rinats.rin.repository.ShiftTemplateRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import javax.persistence.EntityManager

@Service
class ShiftTemplateService(
    @Autowired
    private val templateRepository: ShiftTemplateRepository,
    private val entityManager: EntityManager
) {
    @Transactional
    fun saveShiftTemplate(shiftTemplateFormList: ShiftTemplateFormList): Boolean {
        shiftTemplateFormList.shiftTemplateList.forEach { shiftTemplateForm ->
            val weeksHolidayList =
                listOf("monday", "tuesday", "wednesday", "thursday", "friday", "saturday", "sunday", "holiday")
            weeksHolidayList.forEach {
                save(it, shiftTemplateForm)
            }
        }
        return true
    }

    private fun save(weeksHoliday: String, shiftTemplateForm: ShiftTemplateForm): Boolean {
        var template =
            templateRepository.findById_RoleIdAndWeeksHolidayName(shiftTemplateForm.role ?: return false, weeksHoliday)
                .orElse(null)
        if (template == null) {
            template = ShiftTemplate().also {
                it.id = ShiftTemplateId().apply {
                    roleId = shiftTemplateForm.role
                }
                it.weeksHolidayName = weeksHoliday
                it.numOfPeople = get(weeksHoliday, shiftTemplateForm)
            }
            val a = templateRepository.saveAndFlush(template)
            entityManager.refresh(a)
        } else {
            template.numOfPeople = get(weeksHoliday, shiftTemplateForm)
            val a = templateRepository.saveAndFlush(template)
            entityManager.refresh(a)
        }
        return true
    }

    private fun get(weeksHoliday: String, shiftTemplateForm: ShiftTemplateForm): Int? {
        return when (weeksHoliday) {
            "sunday" -> shiftTemplateForm.sunday
            "monday" -> shiftTemplateForm.monday
            "tuesday" -> shiftTemplateForm.tuesday
            "wednesday" -> shiftTemplateForm.wednesday
            "thursday" -> shiftTemplateForm.thursday
            "friday" -> shiftTemplateForm.friday
            "saturday" -> shiftTemplateForm.saturday
            "holiday" -> shiftTemplateForm.holiday
            else -> return null
        }
    }
}