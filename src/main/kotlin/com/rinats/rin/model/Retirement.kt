package com.rinats.rin.model

import java.util.*
import javax.persistence.Entity
import javax.persistence.Id
import javax.persistence.Table

@Entity
@Table(name = "employee_retirement")
data class Retirement (
    @Id
    val employee_id: String,
    val date: Date
)