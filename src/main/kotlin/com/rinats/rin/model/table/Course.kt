package com.rinats.rin.model.table

import javax.persistence.Entity
import javax.persistence.Id
import javax.persistence.Table

@Entity
@Table(name = "course")
data class Course (
    @Id
    val id: String,
    val name: String
)
