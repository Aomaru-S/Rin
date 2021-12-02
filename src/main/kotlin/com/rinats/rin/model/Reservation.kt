package com.rinats.rin.model

import org.hibernate.annotations.Formula
import org.springframework.format.annotation.DateTimeFormat
import java.util.Date
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.Id
import javax.persistence.Table

@Entity
@Table(name = "reservation")
data class Reservation (
    @Id
    @Formula(value = "")
    val id: String,
    @Column(name = "customer_name")
    val customerName: String,
    @Column(name = "course_id")
    val courseId: String,
    @Column(name = "date_time")
    @DateTimeFormat(pattern = "yyyy/MM/dd")
    val dateTime: Date,
    @Column(name = "num_of_people")
    val numOfPeople: Int,
    @Column(name = "employee_id")
    val employeeId: String,
    @Column(name = "table_name")
    val tableName: String
)