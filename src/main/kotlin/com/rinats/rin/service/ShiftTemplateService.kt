package com.rinats.rin.service

import com.rinats.rin.model.form.ShiftTemplateFormList
import com.rinats.rin.model.table.ShiftTemplate
import com.rinats.rin.repository.RoleRepository
import com.rinats.rin.repository.ShiftTemplateRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class ShiftTemplateService(
    @Autowired
    private val templateRepository: ShiftTemplateRepository,
    private val roleRepository: RoleRepository
) {
    fun saveShiftTemplate(shiftTemplateFormList: ShiftTemplateFormList): Boolean {
        shiftTemplateFormList.shiftTemplateList.forEach {
            val shiftTemplate = ShiftTemplate()
            shiftTemplate.id?.roleId = roleRepository.findById(it.role ?: return false).orElse(null).id ?: return false
            shiftTemplate.weeksAndHolidayName = "sunday"
            shiftTemplate.numOfPeople = it.sunday
            templateRepository.save(shiftTemplate)
            shiftTemplate.weeksAndHolidayName = "monday"
            shiftTemplate.numOfPeople = it.sunday
            templateRepository.save(shiftTemplate)
            shiftTemplate.weeksAndHolidayName = "tuesday"
            shiftTemplate.numOfPeople = it.sunday
            templateRepository.save(shiftTemplate)
            shiftTemplate.weeksAndHolidayName = "wednesday"
            shiftTemplate.numOfPeople = it.sunday
            templateRepository.save(shiftTemplate)
            shiftTemplate.weeksAndHolidayName = "thursday"
            shiftTemplate.numOfPeople = it.sunday
            templateRepository.save(shiftTemplate)
            shiftTemplate.weeksAndHolidayName = "friday"
            shiftTemplate.numOfPeople = it.sunday
            templateRepository.save(shiftTemplate)
            shiftTemplate.weeksAndHolidayName = "saturday"
            shiftTemplate.numOfPeople = it.sunday
            templateRepository.save(shiftTemplate)
            shiftTemplate.weeksAndHolidayName = "holiday"
            shiftTemplate.numOfPeople = it.holiday
            templateRepository.save(shiftTemplate)
        }

        return true
    }
}