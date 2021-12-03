package com.rinats.rin.controller

import com.rinats.rin.annotation.NonAuth
import com.rinats.rin.model.Employee
import com.rinats.rin.model.Setting
import com.rinats.rin.repository.SettingRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.mail.MailSender
import org.springframework.mail.SimpleMailMessage
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.ModelAttribute
import org.springframework.web.bind.annotation.RestController
import javax.servlet.http.HttpServletRequest

@RestController
class TestRestController(
    @Autowired
    private val sender: MailSender,
    private val settingRepository: SettingRepository
) {

    @NonAuth
    @GetMapping("/public")
    fun index(): Map<String, String> {
        return HashMap(hashMapOf("text" to "Hello, World! This is public page."))
    }

    @GetMapping("/private")
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

    @NonAuth
    @GetMapping("/setting_test")
    fun settingTest(
        @ModelAttribute setting: Setting
    ) {
        settingRepository.save(setting)
    }
}