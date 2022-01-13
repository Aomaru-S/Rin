package com.rinats.rin.model

import java.time.Instant
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.Id
import javax.persistence.Table

@Entity
@Table(name = "retirement")
open class Retirement {
    @Id
    @Column(name = "id", nullable = false)
    open var id: String? = null

    @Column(name = "date")
    open var date: Instant? = null
}