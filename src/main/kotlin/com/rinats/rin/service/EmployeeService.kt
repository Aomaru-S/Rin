package com.rinats.rin.service

import com.rinats.rin.model.Employee
import com.rinats.rin.model.TentativeEmployee
import com.rinats.rin.model.form.AddTentativeEmployeeForm
import com.rinats.rin.repository.EmployeeRepository
import com.rinats.rin.repository.SequenceNumberRepository
import com.rinats.rin.repository.TentativeEmployeeRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import java.text.SimpleDateFormat

@Service
class EmployeeService(
    @Autowired
    private val tentativeEmployeeRepository: TentativeEmployeeRepository,
    private val sequenceNumberRepository: SequenceNumberRepository,
    private val employeeRepository: EmployeeRepository
) {
    fun addEmployee(addTentativeEmployeeForm: AddTentativeEmployeeForm) {
        val employeeId = sequenceNumberRepository.findById("employee_id").get().nextNumber
        val sdf = SimpleDateFormat("yyyy-MM-dd")
        val birthday = sdf.parse(addTentativeEmployeeForm.birthday)
        val tentativeEmployee = TentativeEmployee(
            employeeId,
            addTentativeEmployeeForm.firstName,
            addTentativeEmployeeForm.lastName,
            addTentativeEmployeeForm.gender,
            birthday,
            "2",
            addTentativeEmployeeForm.mailAddress
        )
        tentativeEmployeeRepository.save(tentativeEmployee)
        val sequenceNumber = sequenceNumberRepository.findById("employee_id").get()
        sequenceNumber.nextNumber++
        sequenceNumberRepository.save(sequenceNumber)
    }

    fun getEmployee(employeeId: String): Employee? {
        return employeeRepository.findById(employeeId).orElse(null)
    }

    fun getEmployeeList(): List<Employee> {
        return employeeRepository.findAll()
    }

    fun retireEmployee(employeeId: String) {
        val employee = employeeRepository.findById(employeeId).orElse(null) ?: return

    }
}
