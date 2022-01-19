package com.rinats.rin.model.table

import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.Id
import javax.persistence.Table

@Entity
@Table(name = "gender")
open class Gender {
    @Id
    @Column(name = "gender_id", nullable = false)
    open var id: Int? = null

    @Column(name = "gender_name", nullable = false, length = 32)
    open var genderName: String? = null
}