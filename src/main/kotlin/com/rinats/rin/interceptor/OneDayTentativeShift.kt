package com.rinats.rin.interceptor

import com.rinats.rin.model.table.Employee
import com.rinats.rin.model.table.Role
import java.util.*

class OneDayTentativeShift(
    date: Date,
    role: Role,
    employeeList: MutableList<Employee>
) : OneDayShiftBase(date, role, employeeList) {
    var isEmployeeInsufficient: Boolean = false //人数不足
    var isLaborInsufficient: Boolean = false //労働力不足
}