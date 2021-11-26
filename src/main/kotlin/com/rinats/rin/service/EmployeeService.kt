package com.rinats.rin.service

import com.rinats.rin.model.AuthInfo
import com.rinats.rin.model.Employee
import com.rinats.rin.model.Retirement
import com.rinats.rin.model.form.AddEmployeeForm
import com.rinats.rin.repository.AuthInfoRepository
import com.rinats.rin.repository.EmployeeRepository
import com.rinats.rin.repository.RetirementRepository
import com.rinats.rin.repository.SequenceNumberRepository
import com.rinats.rin.util.AuthUtil
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.mail.MailSender
import org.springframework.mail.SimpleMailMessage
import org.springframework.stereotype.Service
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.HashMap

@Service
class EmployeeService(
    @Autowired
    private val sequenceNumberRepository: SequenceNumberRepository,
    private val employeeRepository: EmployeeRepository,
    private val authInfoRepository: AuthInfoRepository,
    private val retirementRepository: RetirementRepository,
    private val sender: MailSender
) {
    //    従業員仮登録処理
    fun addTentativeEmployee(addEmployeeForm: AddEmployeeForm) {
        val employeeId = getAndUpdateSequence()
        val sdf = SimpleDateFormat("yyyy-MM-dd")
        val birthday = sdf.parse(addEmployeeForm.birthday)

        val employee = Employee(
            employeeId.toString(),
            addEmployeeForm.firstName ?: "",
            addEmployeeForm.lastName ?: "",
            addEmployeeForm.gender,
            birthday,
            1000,
            false,
            "3",
            addEmployeeForm.mailAddress ?: ""
        )

        val date = Date().apply {
            time = 0
        }
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


        sendMail(addEmployeeForm, employeeId, password)
    }

    //    従業員本登録処理
    fun definitiveRegistration(employeeId: String): Boolean {
        val employee = employeeRepository.findById(employeeId).orElse(null) ?: return false
        if (employee.roleId == "3") {
            employee.roleId = "2"
            employeeRepository.save(employee)
            return true
        }
        return false
    }

    //    従業員情報取得処理
    fun getEmployee(employeeId: String): Employee? {
        return employeeRepository.findById(employeeId).orElse(null)
    }

    //    従業員一覧取得処理
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

    fun changeMailAddress(employeeId: String, mailAddress: String): Boolean {
        val employee = employeeRepository.findById(employeeId).orElse(null) ?: return false
        employee.mailAddress = mailAddress
        employeeRepository.save(employee)
        return true
    }

    private fun getAndUpdateSequence(): Int {
        val next = sequenceNumberRepository.findById("employee_id").get().nextNumber
        val sequenceNumber = sequenceNumberRepository.findById("employee_id").get()
        sequenceNumber.nextNumber++
        sequenceNumberRepository.save(sequenceNumber)
        return next
    }

    private fun sendMail(
        addEmployeeForm: AddEmployeeForm,
        employeeId: Int,
        password: String
    ) {
        val message = SimpleMailMessage()
        message.setFrom("info@rin-ats.com")
        message.setTo(addEmployeeForm.mailAddress)
        message.setSubject("仮登録のお知らせ")
        message.setText(
            "Rinシステムへの仮登録が完了しました。\n" +
                    "従業員IDとパスワードは以下のとおりです。\n" +
                    "従業員ID: $employeeId\n" +
                    "パスワード: $password"
        )
        sender.send(message)
    }
}
