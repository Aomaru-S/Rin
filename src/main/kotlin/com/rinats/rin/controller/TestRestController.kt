package com.rinats.rin.controller

import com.rinats.rin.annotation.NonAuth
import com.rinats.rin.model.Employee
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.mail.MailSender
import org.springframework.mail.SimpleMailMessage
import org.springframework.web.bind.annotation.*
import javax.servlet.http.HttpServletRequest
import kotlin.collections.HashMap

@RestController
class TestRestController(
    @Autowired
    private val sender: MailSender
) {

    @NonAuth
    @GetMapping("/public")
    fun index(): Map<String, String> {
        return HashMap(hashMapOf("text" to "Hello, World! This is public page."))
    }

    @PostMapping("/private")
    fun private(): Map<String, String> {
        return HashMap(hashMapOf("text" to "Hello, World! This is private page."))
    }

    @NonAuth
    @GetMapping("/mail")
    fun mail() {
        val message = SimpleMailMessage()
        message.setFrom("test@rin-ats.com")
        message.setTo("yggdrasill0430@gmail.com")
        message.setSubject("Hello, penis")
        message.setText("Hello, Souhei")

        sender.send(message)
    }

    @GetMapping("/employee")
    fun employee(request: HttpServletRequest): Employee {
        return request.getAttribute("employee") as Employee
    }

    @NonAuth
    @GetMapping("/test")
    fun test() {
        println("Hellooooooooooooooooooooooooooooooooooooooooooo")
    }
}