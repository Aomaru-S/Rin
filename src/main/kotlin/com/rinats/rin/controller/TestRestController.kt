package com.rinats.rin.controller

import com.rinats.rin.annotation.NonAuth
import com.rinats.rin.model.table.Setting
import com.rinats.rin.repository.EmployeeRepository
import com.rinats.rin.repository.SettingRepository
import com.rinats.rin.service.ShiftGeneratorService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.mail.MailSender
import org.springframework.mail.SimpleMailMessage
import org.springframework.web.bind.annotation.*
import javax.servlet.http.HttpServletRequest

@RestController
@CrossOrigin(methods = [RequestMethod.POST, RequestMethod.OPTIONS])
class TestRestController(
    @Autowired
    private val sender: MailSender,
    private val settingRepository: SettingRepository,
    private val employeeRepository: EmployeeRepository,
    private val shiftGeneratorService: ShiftGeneratorService
) {

    @CrossOrigin(methods = [RequestMethod.GET])
    @NonAuth
    @GetMapping("/api/public")
    fun getPublic(
        msg: String?,
    ): String {
//        val list = arrayListOf("a", "b")
//        println(list[-1])
        return "get: $msg"
    }

    //    @CrossOrigin(methods = [RequestMethod.POST, RequestMethod.OPTIONS])
    @NonAuth
    @PostMapping("/public")
    fun public(
        request: HttpServletRequest,
        @RequestParam
        a: String?,
        b: String?
    ): String {
        println("RequestParam: $a")
        println(b)
//        val list = arrayListOf("a", "b")
//        println(list[-1])
        return "{\"a\":\"$a\"}"
    }

    @NonAuth
    @GetMapping("/api/publi")
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
    fun apiPrivate(
        msg: String? = "non text"
    ): HashMap<String, String?> {
        return hashMapOf("result" to msg)
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

    @NonAuth
    @GetMapping("/employee")
    fun employee(
        a: String?
    ): String {
        if (a == null) {
            return "null"
        }
        val employee = employeeRepository.findById(a).orElse(null)
        return employee?.toString() ?: "null"
    }

    @NonAuth
    @GetMapping("/setting_test")
    fun settingTest(
        @ModelAttribute setting: Setting
    ) {
        settingRepository.save(setting)
    }

    @NonAuth
    @GetMapping("/shift_generator")
    fun generateShift() {
//        shiftGeneratorService.shiftGenerator()
    }
}