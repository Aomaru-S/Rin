package com.rinats.rin.controller.storemanager

import com.rinats.rin.model.form.CourseForm
import com.rinats.rin.service.CourseService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.validation.BindingResult
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.ModelAttribute
import org.springframework.web.bind.annotation.PostMapping

@Controller
class CourseController(
    @Autowired
    val courseService: CourseService
) {

    @GetMapping("/course_registration")
    fun courseRegistration(
        @Validated
        @ModelAttribute
        courseRegistrationForm: CourseForm,
        validationResult: BindingResult
    ): String {
        return "CourseRegistration"
    }

    @PostMapping("/course_registration_complete")
    fun courseRegistrationComplete(
        @ModelAttribute
        courseRegistrationForm: CourseForm,
    ): String {
        courseService.courseRegistration(courseRegistrationForm.name ?: "")
        return "redirect:course_check"
    }

    @GetMapping("/course_check")
    fun courseCheck(model: Model): String {
        model.addAttribute("courseList", courseService.getCourse())
        return "CourseCheck"
    }

    @PostMapping("/course_edit")
    fun courseEdit(model: Model, courseForm: CourseForm): String {
        model.addAttribute("id", courseForm.id)
        model.addAttribute("name", courseForm.name)
        return "CourseEdit"
    }

    @PostMapping("/course_edit_conf")
    fun courseEditConf(model: Model, courseForm: CourseForm): String {
        model.addAttribute("id", courseForm.id)
        model.addAttribute("name", courseForm.name)
        return "CourseEditConf"
    }

    @PostMapping("/course_edit_complete")
    fun courseEditComplete(model: Model, courseForm: CourseForm): String {
        courseService.courseUpdate(courseForm.id?.toString() ?: "0", courseForm.name ?: "")
        return "redirect:course_check"
    }

    @PostMapping("course_delete")
    fun courseDelete(model: Model, courseForm: CourseForm): String {
        courseService.courseDelete(courseForm.id?.toString() ?: "0", courseForm.name ?: "")
        return "redirect:course_check"
    }
}