package com.rinats.rin.service

import com.rinats.rin.model.table.EmployeeLabor
import com.rinats.rin.model.table.compositeId.EmployeeLaborId
import com.rinats.rin.repository.EmployeeLaborRepository
import com.rinats.rin.repository.EmployeeRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import java.lang.IllegalStateException

@Service
class LaborService(
    @Autowired
    private val laborRepository: EmployeeLaborRepository,
    private val employeeRepository: EmployeeRepository,
    private val employeeService: EmployeeService
) {

    fun getLabor(): List<EmployeeLabor> {
        return laborRepository.findAll().filter {
            it.id?.roleId != 0 && it.id?.roleId != 3
        }
    }

    fun getName(laborList: List<EmployeeLabor>): MutableList<String> {
        val employeeName: MutableList<String> = mutableListOf()
        laborList.forEach {
            val employee = employeeService.getEmployee(it.id?.employeeId, containerManager = false)
                ?: throw IllegalArgumentException("Can't not find employee from employeeId")
            employeeName.add(employee.lastName + " " + employee.firstName)
        }
        return employeeName
    }

    fun getByName(employeeId: String): String {
        val employee = employeeRepository.findById(employeeId)
        println(employee.get().lastName + " " + employee.get().firstName)
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