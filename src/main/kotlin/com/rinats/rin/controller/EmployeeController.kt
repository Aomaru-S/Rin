package com.rinats.rin.controller

import com.rinats.rin.model.Employee
import com.rinats.rin.model.form.AddTentativeEmployeeForm
import com.rinats.rin.model.form.GetEmployeeForm
import com.rinats.rin.service.EmployeeService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.validation.BindingResult
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.ModelAttribute
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class EmployeeController(
    @Autowired
    val employeeService: EmployeeService
) {

    @PostMapping("/add_employee")
    fun addTentativeEmployee(
        @ModelAttribute @Validated
        addTentativeEmployeeForm: AddTentativeEmployeeForm,
        bindResult: BindingResult
    ) {
        if (bindResult.hasErrors()) {
            return
        }
        employeeService.addEmployee(addTentativeEmployeeForm)
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