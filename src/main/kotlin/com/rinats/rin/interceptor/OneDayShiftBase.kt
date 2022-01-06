package com.rinats.rin.interceptor

import com.rinats.rin.model.Employee
import java.util.*

open class OneDayShiftBase(
    val date: Date,
    val role: Role,
    val employeeList: MutableList<Employee>
)