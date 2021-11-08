package com.rinats.rin.model

import java.util.*
import javax.persistence.Entity
import javax.persistence.Id
import javax.persistence.Table

@Entity
@Table(name = "retirement")
data class Retirement (
    @Id
    val id: String,
    val date: Date
)