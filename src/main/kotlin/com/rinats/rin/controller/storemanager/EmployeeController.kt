package com.rinats.rin.controller.storemanager

import com.rinats.rin.model.other.CompleteMessage
import com.rinats.rin.service.EmployeeService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping

@Controller
@RequestMapping("/employee")
class EmployeeController(
    @Autowired
    val employeeService: EmployeeService
) {
    @GetMapping("")
    fun index(
        model: Model
    ): String {
        val employeeList = employeeService.getEmployeeList()
        model.addAttribute("employeeList", employeeList)
        return "employee_list"
    }

    @GetMapping("/add")
    fun addEmployeeForm(
        model: Model
    ): String {
        return "add_employee"
    }

    @PostMapping("/add_confirm")
    fun addEmployeeConfirm(): String {
        return "add_employee_confirm"
    }

    @PostMapping("/add")
    fun addEmployee(
        model: Model
    ): String {
        val message = CompleteMessage("従業員登録完了: Rin", "従業員が登録されました。")
        model.addAttribute("message", message)
        return "complete"
    }

    @GetMapping("/retirement")
    fun retireEmployeeForm(): String {
        return "EmployeeRetirement"
    }

    @PostMapping("/retirement")
    fun retireEmployee(
        model: Model
    ): String {
        val message = CompleteMessage("従業員退職完了: Rin", "従業員の退職処理が完了しました。")
        model.addAttribute("message", message)
        return "complete"
    }

    @GetMapping("/edit")
    fun editEmployeeForm(employeeId: String?): String {
        return "EmployeeEditing"
    }

    @PostMapping("/edit_confirm")
    fun editEmployeeConfirm(): String {
        return "EmployeeInformationEditingCheck"
    }

    @PostMapping("/edit")
    fun editEmployee(
        model: Model
    ): String {
        val message = CompleteMessage("従業員情報編集完了: Rin", "従業員情報の編集が完了しました。")
        model.addAttribute("message", message)
        return "complete"
    }
}