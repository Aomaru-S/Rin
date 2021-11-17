package com.rinats.rin.service

import com.rinats.rin.model.TentativeAuthInfo
import com.rinats.rin.model.TentativeEmployee
import com.rinats.rin.model.form.AddTentativeEmployeeForm
import com.rinats.rin.repository.*
import com.rinats.rin.util.AuthUtil
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.mail.MailSender
import org.springframework.mail.SimpleMailMessage
import org.springframework.stereotype.Service
import java.text.SimpleDateFormat
import java.util.*

@Service
class TentativeEmployeeService(
    @Autowired
    private val tentativeEmployeeRepository: TentativeEmployeeRepository,
    private val sequenceNumberRepository: SequenceNumberRepository,
    private val tentativeAuthInfoRepository: TentativeAuthInfoRepository,
    private val sender: MailSender
) {

    fun addTentativeEmployee(addTentativeEmployeeForm: AddTentativeEmployeeForm) {
        val employeeId = sequenceNumberRepository.findById("employee_id").get().nextNumber
        val sdf = SimpleDateFormat("yyyy-MM-dd")
        val birthday = sdf.parse(addTentativeEmployeeForm.birthday)
        val tentativeEmployee = TentativeEmployee(
            employeeId,
            addTentativeEmployeeForm.firstName,
            addTentativeEmployeeForm.lastName,
            addTentativeEmployeeForm.gender,
            birthday,
            "2",
            addTentativeEmployeeForm.mailAddress
        )
        val date = Date()
        date.time = 0
        val password = AuthUtil.generatePassword()
        val salt = AuthUtil.generateSalt()
        val tentativeAuthInfo = TentativeAuthInfo(
            employeeId.toString(),
            AuthUtil.getDigest(password, salt),
            AuthUtil.generateSalt(),
            false,
            "",
            date
        )
        tentativeEmployeeRepository.save(tentativeEmployee)
        tentativeAuthInfoRepository.save(tentativeAuthInfo)
        val sequenceNumber = sequenceNumberRepository.findById("employee_id").get()
        sequenceNumber.nextNumber++
        sequenceNumberRepository.save(sequenceNumber)

        val message = SimpleMailMessage()
        message.setFrom("info@rin-ats.com")
        message.setTo(addTentativeEmployeeForm.mailAddress)
        message.setSubject("仮登録のお知らせ")
        message.setText("Rinシステムへの仮登録が完了しました。\n" +
                "従業員IDとパスワードは以下のとおりです。\n" +
                "従業員ID: $employeeId\n" +
                "パスワード: $password")
        sender.send(message)
    }
}