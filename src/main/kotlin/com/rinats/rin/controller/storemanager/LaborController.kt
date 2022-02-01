package com.rinats.rin.controller.storemanager

import com.rinats.rin.model.form.LaborForm
import com.rinats.rin.model.table.Employee
import com.rinats.rin.model.table.EmployeeLabor
import com.rinats.rin.service.EmployeeService
import com.rinats.rin.service.LaborService
import com.rinats.rin.service.RoleService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam

@Controller
class LaborController(
    @Autowired val laborService: LaborService,
    private val roleService: RoleService,
    private val employeeService: EmployeeService
) {
    @GetMapping("labor_select_role")
    fun laborSelectRole(model: Model): String {
        model.addAttribute("roleList", roleService.getRoleList())
        return "LaborSelectRole"
    }

    @PostMapping("labor_check")
    fun laborCheck(@RequestParam("role") roleId: Int, model: Model): String {
        val employeeLaborList = laborService.getRoleLabor(roleId)
        val employeeLaborMap: MutableMap<Employee, EmployeeLabor> = mutableMapOf()
        employeeLaborList.forEach {
            employeeLaborMap[employeeService.getEmployee(it.id?.employeeId)!!] = it
        }
        model.addAttribute("employeeLaborMap", employeeLaborMap)
        return "LaborCheck"
    }

    @PostMapping("labor_edit")
    fun laborEdit(model: Model, employeeLevelForm: LaborForm): String {
        val name = laborService.getByName(employeeLevelForm.employeeId ?: "")
        model.apply {
            addAttribute("employeeId", employeeLevelForm.employeeId)
            addAttribute("roleId", employeeLevelForm.roleId)
            addAttribute("level", employeeLevelForm.level)
            addAttribute("name", name)
        }
        return "LaborEditing"
    }

    @PostMapping("labor_edit_check")
    fun laborEditCheck(model: Model, employeeLevelForm: LaborForm): String {
        val name = laborService.getByName(employeeLevelForm.employeeId ?: "")
        model.apply {
            addAttribute("employeeId", employeeLevelForm.employeeId)
            addAttribute("roleId", employeeLevelForm.roleId)
            addAttribute("level", employeeLevelForm.level)
            addAttribute("name", name)
        }
        return "LaborEditingCheck"
    }

    @PostMapping("labor_edit_complete")
    fun laborEditComplete(model: Model, employeeLevelForm: LaborForm): String {
        employeeLevelForm.apply {
            return if (employeeId != null && roleId != null && level != null) {
                laborService.levelUpdate(employeeId!!, roleId!!, level!!)
                "top"
            } else {
                model.apply {
                    addAttribute("title", "parameter error")
                    addAttribute("message","不正な入力値")
                }
                "error"
            }
        }
    }
}