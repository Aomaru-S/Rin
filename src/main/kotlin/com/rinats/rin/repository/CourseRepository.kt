package com.rinats.rin.repository

import com.rinats.rin.model.table.Course
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface CourseRepository : JpaRepository<Course, String>