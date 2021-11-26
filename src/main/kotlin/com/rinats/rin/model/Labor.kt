package com.rinats.rin.model

import java.io.Serializable
import javax.persistence.*
import javax.persistence.Table

@Entity
@Table(name = "labor")

data class Labor(
    @Id
    @Column(name = "employee_id")
    val employeeId: String,
    @JoinColumn(name = "employee_id")
    @OneToOne(cascade = [CascadeType.ALL],fetch = FetchType.LAZY)
    val employee: Employee,
    val level: Int,
) : Serializable