package com.rinats.rin.controller

import com.rinats.rin.annotation.NonAuth
import com.rinats.rin.model.Employee
import com.rinats.rin.model.Setting
import com.rinats.rin.repository.SettingRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpMethod
import org.springframework.mail.MailSender
import org.springframework.mail.SimpleMailMessage
import org.springframework.web.bind.annotation.*
import javax.servlet.http.HttpServletRequest

@RestController
class TestRestController(
    @Autowired
    private val sender: MailSender,
    private val settingRepository: SettingRepository
) {

    @CrossOrigin(methods = [RequestMethod.GET])
    @NonAuth
    @GetMapping("/public")
    fun getPublic(
        a: String?,
    ): String {
        println(a)
//        val list = arrayListOf("a", "b")
//        println(list[-1])
        return "get: $a"
    }

    @CrossOrigin(methods = [RequestMethod.POST, RequestMethod.OPTIONS])
    @NonAuth
    @PostMapping("/public")
    fun public(
        request: HttpServletRequest,
        @RequestParam
        a: String?
    ): String {
        println("RequestParam: $a")
//        val list = arrayListOf("a", "b")
//        println(list[-1])
        return "{\"a\":\"$a\"}"
    }

    @NonAuth
    @GetMapping("/api/public")
    fun apiPublic(): String {
        return "/api/public"
    }

    @GetMapping("/private")
    fun private(
        a: String?
    ): String {
        println(a)
        return "/private"
    }

    @GetMapping("/api/private")
    fun apiPrivate(): HashMap<String, String> {
        return hashMapOf("result" to "private")
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
    fun employee(
        @RequestAttribute
        employee: Employee
    ): Employee {
        return employee
    }

    @NonAuth
    @GetMapping("/setting_test")
    fun settingTest(
        @ModelAttribute setting: Setting
    ) {
        settingRepository.save(setting)
    }
}