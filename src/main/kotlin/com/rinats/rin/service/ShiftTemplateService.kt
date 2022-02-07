package com.rinats.rin.service

import com.rinats.rin.model.form.ShiftTemplateForm
import com.rinats.rin.model.form.ShiftTemplateFormList
import com.rinats.rin.model.table.ShiftTemplate
import com.rinats.rin.model.table.compositeId.ShiftTemplateId
import com.rinats.rin.repository.ShiftTemplateRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional


@Service
class ShiftTemplateService(
    @Autowired
    private val templateRepository: ShiftTemplateRepository
) {

    fun saveShiftTemplate(shiftTemplateFormList: ShiftTemplateFormList): Boolean {
        val weeksHolidayList =
            listOf("monday", "tuesday", "wednesday", "thursday", "friday", "saturday", "sunday", "holiday")
        shiftTemplateFormList.shiftTemplateList.forEach {
            save(weeksHolidayList, it)
        }
        return true
    }

    @Transactional
    fun save(weeksHolidayList: List<String>, shiftTemplateForm: ShiftTemplateForm): Boolean {
        val test: MutableList<ShiftTemplate> = mutableListOf()
        val test2 = templateRepository.findAll()
        weeksHolidayList.forEach { weeksHoliday ->
//            var template2 = templateRepository.findById_RoleIdAndWeeksHolidayName(shiftTemplateForm.role!!, weeksHoliday).orElse(null)
            var template2 = runCatching {
                test2.single {
                    it.id?.roleId == shiftTemplateForm.role && it.id?.weeksHolidayName.equals(weeksHoliday)
                }
            }.getOrNull()

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

    fun getShiftTemplate(): List<ShiftTemplateForm>? {
        val roleList = mutableSetOf<Int>()
        templateRepository.findAll().forEach {
            roleList.add(it.id?.roleId ?: throw IllegalArgumentException())
        }
        val shiftTemplateFormList = mutableListOf<ShiftTemplateForm>()
        roleList.forEach { roleId ->
            val shiftTemplateForm = ShiftTemplateForm().apply {
                this.role = roleId
            }
            val tmp = templateRepository.findById_RoleId(roleId)
            tmp.forEach {
                when (it.id?.weeksHolidayName) {
                    "sunday" -> shiftTemplateForm.sunday = it.numOfPeople ?: return null
                    "monday" -> shiftTemplateForm.monday = it.numOfPeople ?: return null
                    "tuesday" -> shiftTemplateForm.tuesday = it.numOfPeople ?: return null
                    "wednesday" -> shiftTemplateForm.wednesday = it.numOfPeople ?: return null
                    "thursday" -> shiftTemplateForm.thursday = it.numOfPeople ?: return null
                    "friday" -> shiftTemplateForm.friday = it.numOfPeople ?: return null
                    "saturday" -> shiftTemplateForm.saturday = it.numOfPeople ?: return null
                    "holiday" -> shiftTemplateForm.holiday = it.numOfPeople ?: return null
                }
            }
            shiftTemplateFormList.add(shiftTemplateForm)
        }
        return shiftTemplateFormList
    }
}