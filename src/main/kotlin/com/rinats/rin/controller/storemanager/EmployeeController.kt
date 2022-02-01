package com.rinats.rin.controller.storemanager

import com.rinats.rin.model.form.AddEmployeeForm
import com.rinats.rin.model.form.UpdateEmployeeForm
import com.rinats.rin.model.other.CompleteMessage
import com.rinats.rin.service.EmployeeService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.validation.BindingResult
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.*

@Controller
@RequestMapping("/employee")
class EmployeeController(
    @Autowired
    val employeeService: EmployeeService
) {
    @GetMapping("")
    fun index(
        model: Model,
        @RequestParam(name = "is_retirement", required = false) _isRetirement: String? = "false",
        @RequestParam(name = "is_tentative", required = false) _isTentative: String? = "false"
    ): String {
        val isRetirement = _isRetirement?.toBooleanStrict() ?: false
        val isTentative = _isTentative?.toBooleanStrict() ?: false

        val employeeList = employeeService.getEmployeeList(isTentative, isRetirement)
        model.addAttribute("employeeList", employeeList)
        model.addAttribute("isRetirement", isRetirement)
        model.addAttribute("isTentative", isTentative)
        return "employee_list"
    }

    @GetMapping("/add")
    fun addEmployeeForm(
        model: Model
    ): String {
        model.addAttribute("employeeForm", AddEmployeeForm())
        return "add_employee"
    }

    @PostMapping("/add_confirm")
    fun addEmployeeConfirm(
        @Validated
        @ModelAttribute
        addEmployeeForm: AddEmployeeForm,
        bindingResult: BindingResult,
        model: Model
    ): String {
        if (bindingResult.hasErrors()) {
            return "redirect:/employee/add?validation_error"
        }
        model.addAttribute("addEmployeeForm", addEmployeeForm)
        return "add_employee_confirm"
    }

    @PostMapping("/add")
    fun addEmployee(
        @Validated
        @ModelAttribute
        addEmployeeForm: AddEmployeeForm,
        bindingResult: BindingResult,
        model: Model
    ): String {
        if (bindingResult.hasErrors()) {
            return "redirect:/employee/add?validation_error"
        }
        val result = employeeService.addTentativeEmployee(addEmployeeForm)
        if (!result) {
            return "redirect:/employee/add?invalid_address"
        }
        val message = CompleteMessage("従業員登録完了: Rin", "従業員が登録されました。")
        model.addAttribute("message", message)
        return "complete"
    }

    @GetMapping("/retirement")
    fun retireEmployeeForm(
        model: Model,
        @RequestParam(name = "employee_id", required = false) employeeId: String?
    ): String {
        val employee = employeeService.getEmployee(employeeId, containRetirement = true)
            ?: return "redirect:/employee?invalid_employee_id"
        model.addAttribute("employee", employee)
        return "EmployeeRetirement"
    }

    @PostMapping("/retirement")
    fun retireEmployee(
        model: Model,
        @RequestParam(name = "employee_id", required = false) employeeId: String?
    ): String {
        val result = employeeService.retireEmployee(employeeId)
        if (!result) {
            return "redirect:/employee?invalid_employee_id"
        }
        val message = CompleteMessage("従業員退職完了: Rin", "従業員の退職処理が完了しました。")
        model.addAttribute("message", message)
        return "complete"
    }

    @GetMapping("/update")
    fun updateEmployeeForm(
        @RequestParam("employee_id")
        employeeId: String?,
        model: Model
    ): String {
        val employee = employeeService.getEmployee(employeeId) ?: return "redirect:/employee"
        model.addAttribute("employeeForm", AddEmployeeForm(employee))
        model.addAttribute("employeeId", employeeId)
        return "update_employee"
    }

    @PostMapping("/update_confirm")
    fun updateEmployeeConfirm(
        @Validated
        @ModelAttribute
        updateEmployeeForm: UpdateEmployeeForm,
        bindingResult: BindingResult,
        model: Model,
        @RequestParam
        employeeId: String?
    ): String {
        if (bindingResult.hasErrors()) {
            return "redirect:/employee/update?employee_id=${employeeId}&validation_error"
        }
        model.addAttribute("employeeForm", updateEmployeeForm)
        model.addAttribute("employeeId", employeeId)
        return "update_employee_confirm"
    }

    @PostMapping("/update")
    fun updateEmployee(
        @Validated
        @ModelAttribute
        updateEmployeeForm: UpdateEmployeeForm,
        bindingResult: BindingResult,
        model: Model,
        @RequestParam
        employeeId: String?
    ): String {
        if (bindingResult.hasErrors()) {
            return "redirect:/employee/update?employee_id=${employeeId}&validation_error"
        }
        val result = employeeService.updateEmployee(employeeId, updateEmployeeForm)
        if (!result) {
            return "redirect:/employee/update?invalid_address"
        }
        val message = CompleteMessage("従業員情報変更完了: Rin", "従業員情報が変更されました。")
        model.addAttribute("message", message)
        return "complete"
    }

    @GetMapping("/register")
    fun registerEmployeeForm(
        @RequestParam("employee_id")
        employeeId: String?,
        model: Model
    ): String {
        val employee = employeeService.getEmployee(
            employeeId = employeeId,
            containTentative = true
        ) ?: return "redirect:/employee?error"
        model.addAttribute("employee", employee)
        return "register_employee"
    }

    @PostMapping("/register")
    fun registerEmployee(
        @RequestParam("employeeId")
        employeeId: String?,
        model: Model
    ): String {
        val result = employeeService.registerEmployee(employeeId)
        if (!result) {
            return "redirect:/employee/register?error"
        }
        val message = CompleteMessage("従業員本登録完了: Rin", "従業員が本登録されました。")
        model.addAttribute("message", message)
        return "complete"
    }
}