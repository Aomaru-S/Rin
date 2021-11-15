package com.rinats.rin.service

import com.rinats.rin.model.TentativeEmployee
import com.rinats.rin.model.form.AddEmployeeForm
import com.rinats.rin.repository.EmployeeRepository
import com.rinats.rin.repository.TentativeEmployeeRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class EmployeeService(
    @Autowired
    private val tentativeEmployeeRepository: TentativeEmployeeRepository
) {
    fun addEmployee(addEmployeeForm: AddEmployeeForm) {
        println(addEmployeeForm)
    }
}
