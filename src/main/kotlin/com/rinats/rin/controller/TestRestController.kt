package com.rinats.rin.controller

import com.rinats.rin.annotation.NonAuth
import com.rinats.rin.model.Employee
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.ModelAttribute
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RestController
import java.util.*
import javax.servlet.http.HttpServletRequest
import kotlin.collections.HashMap

@RestController
class TestRestController {

    @NonAuth
    @PostMapping("/public")
    fun index(): Map<String, String> {
        return HashMap(hashMapOf("text" to "Hello, World! This is public page."))
    }

    @PostMapping("/private")
    fun private(): Map<String, String> {
        return HashMap(hashMapOf("text" to "Hello, World! This is private page."))
    }
}