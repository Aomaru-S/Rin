package com.rinats.rin.model.table

import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.Id
import javax.persistence.Table

@Entity
@Table(name = "sequence_number")
data class SequenceNumber(
    @Id
    @Column(name = "sequence_key")
    val sequenceKey: String,
    @Column(name = "next_number")
    var nextNumber: Int
)