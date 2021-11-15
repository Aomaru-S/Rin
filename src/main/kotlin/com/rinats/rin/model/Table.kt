package com.rinats.rin.model

import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.Id
import javax.persistence.Table

@Entity
@Table(name = "`table`")
data class Table (
    @Id
    var name: String,
    @Column(name = "num_of_people")
    var numOfPeople: Int
)
