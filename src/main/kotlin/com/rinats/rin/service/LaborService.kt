package com.rinats.rin.service

import com.rinats.rin.model.table.EmployeeLabor
import com.rinats.rin.model.table.compositeId.EmployeeLaborId
import com.rinats.rin.repository.EmployeeLaborRepository
import com.rinats.rin.repository.EmployeeRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class LaborService(
    @Autowired
    private val laborRepository: EmployeeLaborRepository,
    private val employeeRepository: EmployeeRepository
) {

    fun getLabor(): MutableList<EmployeeLabor> {
        return laborRepository.findAll()
    }

    fun getRoleLabor(roleId: Int): List<EmployeeLabor> {
        return laborRepository.findById_RoleId(roleId)
    }

    fun getByName(employeeId: String): String {
        val employee = employeeRepository.findById(employeeId)
        return employee.get().lastName + " " + employee.get().firstName
    }

    fun levelUpdate(employeeId: String, roleId: String, labor: Int) {
        val laborId = EmployeeLaborId().also {
            it.employeeId = employeeId
            it.roleId = roleId.toInt()
        }
        val employeeLabor = EmployeeLabor()
        employeeLabor.id = laborId
        employeeLabor.labor = labor
        laborRepository.save(employeeLabor)
    }
}