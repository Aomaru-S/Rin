package com.rinats.rin.service

import com.rinats.rin.model.form.ShiftTemplateForm
import com.rinats.rin.model.form.ShiftTemplateFormList
import com.rinats.rin.model.table.ShiftTemplate
import com.rinats.rin.model.table.compositeId.ShiftTemplateId
import com.rinats.rin.repository.ShiftTemplateRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import org.springframework.transaction.PlatformTransactionManager
import org.springframework.transaction.annotation.Transactional


@Service
class ShiftTemplateService(
    @Autowired
    private val templateRepository: ShiftTemplateRepository,
    private val transactionManager: PlatformTransactionManager
) {

    /*fun saveShiftTemplate(shiftTemplateFormList: ShiftTemplateFormList): Boolean {
        shiftTemplateFormList.shiftTemplateList.forEach { shiftTemplateForm ->
            val weeksHolidayList =
                listOf("monday", "tuesday", "wednesday", "thursday", "friday", "saturday", "sunday", "holiday")
            weeksHolidayList.forEach {
                save(it, shiftTemplateForm)
            }
        }
        return true
    }*/

    /*fun save(weeksHoliday: String, shiftTemplateForm: ShiftTemplateForm): Boolean {
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
            templateRepository.save(template)
        } else {
            template.numOfPeople = get(weeksHoliday, shiftTemplateForm)
            templateRepository.save(template)
        }
        return true
    }*/

    fun saveShiftTemplate2(shiftTemplateFormList: ShiftTemplateFormList): Boolean {
        val weeksHolidayList =
            listOf("monday", "tuesday", "wednesday", "thursday", "friday", "saturday", "sunday", "holiday")
        shiftTemplateFormList.shiftTemplateList.forEach {
            save2(weeksHolidayList, it)
        }
        return true
    }

    @Transactional
    fun save2(weeksHolidayList: List<String>, shiftTemplateForm: ShiftTemplateForm): Boolean {
        val test: MutableList<ShiftTemplate> = mutableListOf()
        val test2    = templateRepository.findAll()
        weeksHolidayList.forEach { weeksHoliday ->
//            var template2 = templateRepository.findById_RoleIdAndWeeksHolidayName(shiftTemplateForm.role!!, weeksHoliday).orElse(null)
            var template2 = runCatching{ test2.single{ it.id?.roleId == shiftTemplateForm.role && it.id?.weeksHolidayName.equals(weeksHoliday) } }.getOrNull()

            if (template2 == null) {
                template2 = ShiftTemplate().also {
                    it.id = ShiftTemplateId().apply {
                        roleId = shiftTemplateForm.role
                        weeksHolidayName = weeksHoliday
                    }
                    it.numOfPeople = get(weeksHoliday, shiftTemplateForm)
                }
                test.add(template2)
            } else {
                template2.numOfPeople = get(weeksHoliday, shiftTemplateForm)
                test.add(template2)
            }
        }
        templateRepository.saveAll(test)
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