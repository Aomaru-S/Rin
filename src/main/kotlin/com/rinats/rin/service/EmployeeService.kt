package com.rinats.rin.service

import com.rinats.rin.model.AuthInfo
import com.rinats.rin.model.Employee
import com.rinats.rin.model.Retirement
import com.rinats.rin.model.form.AddEmployeeForm
import com.rinats.rin.repository.*
import com.rinats.rin.util.AuthUtil
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.mail.MailSender
import org.springframework.mail.SimpleMailMessage
import org.springframework.stereotype.Service
import java.text.SimpleDateFormat
import java.util.*

@Service
class EmployeeService(
    @Autowired
    private val sequenceNumberRepository: SequenceNumberRepository,
    private val employeeRepository: EmployeeRepository,
    private val authInfoRepository: AuthInfoRepository,
    private val retirementRepository: RetirementRepository,
    private val sender: MailSender
) {
    fun addEmployee(addEmployeeForm: AddEmployeeForm) {
        val employeeId = sequenceNumberRepository.findById("employee_id").get().nextNumber
        val sdf = SimpleDateFormat("yyyy-MM-dd")
        val birthday = sdf.parse(addEmployeeForm.birthday)
        val employee = Employee(
            employeeId.toString(),
            addEmployeeForm.firstName ?: "",
            addEmployeeForm.lastName ?: "",
            addEmployeeForm.gender,
            birthday,
            false,
            "2"
        )
        val date = Date()
        date.time = 0
        val password = AuthUtil.generatePassword()
        val salt = AuthUtil.generateSalt()
        val authInfo = AuthInfo(
            employeeId.toString(),
            AuthUtil.getDigest(password, salt),
            AuthUtil.generateSalt(),
            false,
            "",
            date
        )
        employeeRepository.save(employee)
        authInfoRepository.save(authInfo)
        val sequenceNumber = sequenceNumberRepository.findById("employee_id").get()
        sequenceNumber.nextNumber++
        sequenceNumberRepository.save(sequenceNumber)

        val message = SimpleMailMessage()
        message.setFrom("info@rin-ats.com")
        message.setTo(addEmployeeForm.mailAddress)
        message.setSubject("仮登録のお知らせ")
        message.setText("Rinシステムへの仮登録が完了しました。\n" +
                "従業員IDとパスワードは以下のとおりです。\n" +
                "従業員ID: $employeeId\n" +
                "パスワード: $password")
        sender.send(message)
    }

    fun getEmployee(employeeId: String): Employee? {
        return employeeRepository.findById(employeeId).orElse(null)
    }

    fun getEmployeeList(): List<Employee> {
        return employeeRepository.findAll()
    }

    fun retireEmployee(employeeId: String): Boolean {
        val employee = employeeRepository.findById(employeeId).orElse(null) ?: return false
        val retire = Retirement(employee)
        retirementRepository.save(retire)
        employeeRepository.deleteById(employeeId)
        return true
    }
}
