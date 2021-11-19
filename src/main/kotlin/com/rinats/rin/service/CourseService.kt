package com.rinats.rin.service

import com.rinats.rin.model.Course
import com.rinats.rin.repository.CourseRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class CourseService(@Autowired private val courseRepository: CourseRepository) {

    fun courseRegistration(name: String) {
        if (courseRepository.findAll().isNullOrEmpty()) {
            courseRepository.save(Course("1", name))
        } else {
            val id = Integer.parseInt(courseRepository.findAll().last().id) + 1
            val course = Course(id.toString(), name)
            courseRepository.save(course)
        }
    }

    fun getCourse(): List<Course> {
        return courseRepository.findAll()
    }

    fun courseUpdate(id: String, name: String) {
        val course = Course(id, name)
        courseRepository.save(course)
    }
}