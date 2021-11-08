package com.rinats.rin.model

import java.util.*
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.Id
import javax.persistence.Table

@Entity
@Table(name = "tentative_employee")
data class TentativeEmployee(
    @Id
    val id: String,
    @Column(name = "first_name")
    val firstName: String,
    @Column(name = "last_name")
    val lastName: String,
    val gender: Boolean,
    val birthday: Date,
    @Column(name = "role_id")
    val roleId: String
)