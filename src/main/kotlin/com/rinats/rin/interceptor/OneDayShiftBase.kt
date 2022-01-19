package com.rinats.rin.interceptor

import com.rinats.rin.model.table.Employee
import com.rinats.rin.model.table.Role
import java.util.*

open class OneDayShiftBase(
    val date: Date,
    val role: Role,
    val employeeList: MutableList<Employee>
)