package com.rinats.rin.controller

import com.rinats.rin.annotation.NonAuth
import com.rinats.rin.model.form.CourseForm
import com.rinats.rin.service.CourseService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.ModelAttribute
import org.springframework.web.bind.annotation.PostMapping

@Controller
class CourseController(
    @Autowired
    val courseService: CourseService
) {

    @NonAuth
    @PostMapping("/course_registration")
    fun courseRegistration(
        @ModelAttribute
        courseRegistrationForm: CourseForm,
    ): String {
        courseService.courseRegistration(courseRegistrationForm.name ?: "")
        return "TopPage"
    }

    @NonAuth
    @GetMapping("/course_check")
    fun courseCheck(model: Model): String {
        model.addAttribute("courseList", courseService.getCourse())
        return "CourceCheckPage"
    }

    @NonAuth
    @PostMapping("/cource_edit")
    fun courseEdit(model: Model, courseForm: CourseForm): String {
        model.addAttribute("id", courseForm.id)
        model.addAttribute("name", courseForm.name)
        return "CourseEditPage"
    }

    @NonAuth
    @PostMapping("/cource_edit_conf")
    fun courseEditConf(model: Model, courseForm: CourseForm): String {
        model.addAttribute("id", courseForm.id)
        model.addAttribute("name", courseForm.name)
        return "CourseEditConfPage"
    }

    @NonAuth
    @PostMapping("/cource_edit_complete")
    fun courseEditComplete(model: Model, courseForm: CourseForm): String {
        courseService.courseUpdate(courseForm.id?.toString() ?: "0", courseForm.name ?: "")
        return "CourseEditConfPage"
    }
} 