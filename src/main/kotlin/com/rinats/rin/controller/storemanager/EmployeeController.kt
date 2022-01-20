package com.rinats.rin.controller.storemanager

import com.rinats.rin.model.table.Employee
import com.rinats.rin.model.form.AddEmployeeForm
import com.rinats.rin.model.form.GetEmployeeForm
import com.rinats.rin.model.other.CompleteMessage
import com.rinats.rin.service.EmployeeService
import org.apache.ibatis.annotations.Delete
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
    fun index(): String {
        return "EmployeeInformationCheck"
    }

    @GetMapping("/add")
    fun addEmployeeForm(): String {
        return "EmployeeRegistration"
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

    @PostMapping("")
    fun addEmployee(
        @ModelAttribute @Validated
        addEmployeeForm: AddEmployeeForm,
        bindResult: BindingResult
    ) {
        if (bindResult.hasErrors()) {
            return
        }
        employeeService.addTentativeEmployee(addEmployeeForm)
    }

    @PostMapping("/get_employee")
    fun getEmployee(
        @ModelAttribute @Validated
        getEmployeeForm: GetEmployeeForm,
        bindResult: BindingResult
    ): HashMap<String, Employee?> {
        val employee = if (bindResult.hasErrors()) {
            null
        } else {
            employeeService.getEmployee(getEmployeeForm.employeeId)
        }
        return hashMapOf("employee" to employee)
    }

    @PostMapping("/get_employee_list")
    fun getEmployeeList(): List<Employee> {
        return employeeService.getEmployeeList()
    }
}