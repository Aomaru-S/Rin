package com.rinats.rin.service

import com.rinats.rin.model.SequenceNumber
import com.rinats.rin.model.TentativeEmployee
import com.rinats.rin.model.form.AddEmployeeForm
import com.rinats.rin.repository.SequenceNumberRepository
import com.rinats.rin.repository.TentativeEmployeeRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import java.text.SimpleDateFormat

@Service
class EmployeeService(
    @Autowired
    private val tentativeEmployeeRepository: TentativeEmployeeRepository,
    private val sequenceNumberRepository: SequenceNumberRepository
) {
    fun addEmployee(addEmployeeForm: AddEmployeeForm) {
        val employeeId = sequenceNumberRepository.findById("employee_id").get().nextNumber
        val sdf = SimpleDateFormat("yyyy-MM-dd")
        val birthday = sdf.parse(addEmployeeForm.birthday)
        val tentativeEmployee = TentativeEmployee(
            employeeId,
            addEmployeeForm.firstName,
            addEmployeeForm.lastName,
            addEmployeeForm.gender,
            birthday,
            "2",
            addEmployeeForm.mailAddress
        )
        tentativeEmployeeRepository.save(tentativeEmployee)
        val sequenceNumber = sequenceNumberRepository.findById("employee_id").get()
        sequenceNumber.nextNumber++
        sequenceNumberRepository.save(sequenceNumber)
    }
}
