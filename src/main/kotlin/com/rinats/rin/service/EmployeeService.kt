package com.rinats.rin.service

import com.rinats.rin.model.*
import com.rinats.rin.model.compositeKey.LaborId
import com.rinats.rin.model.form.AddEmployeeForm
import com.rinats.rin.repository.*
import com.rinats.rin.util.AuthUtil
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.mail.MailSender
import org.springframework.mail.SimpleMailMessage
import org.springframework.stereotype.Service
import java.text.SimpleDateFormat
import java.time.ZoneId
import java.util.*

@Service
class EmployeeService(
    @Autowired
    private val sequenceNumberRepository: SequenceNumberRepository,
    private val employeeRepository: EmployeeRepository,
    private val authInfoRepository: AuthInfoRepository,
    private val retirementRepository: RetirementRepository,
    private val sender: MailSender,
    private val employeeLaborRepository: EmployeeLaborRepository,
    private val roleRepository: RoleRepository,
    private val genderRepository: GenderRepository
) {
    //    従業員仮登録処理
    fun addTentativeEmployee(addEmployeeForm: AddEmployeeForm) {
        val employeeId = getAndUpdateSequence().toString()
        val sdf = SimpleDateFormat("yyyy-MM-dd")
        val birthday = sdf.parse(addEmployeeForm.birthday)

        val labor = Labor()
        labor.id = LaborId(employeeId, "2")
        labor.level = 2
        val laborList = arrayListOf<Labor>()
        laborList.add(labor)
        val employee = Employee().also {
            it.id = employeeId
            it.firstName = addEmployeeForm.firstName
            it.lastName = addEmployeeForm.lastName
            it.birthday = birthday.toInstant().atZone(ZoneId.systemDefault()).toLocalDate()
            it.hourlyWage = 1000
            it.isAndroidNotification = false
            it.mailAddress = addEmployeeForm.mailAddress
            it.isTentative = false
            it.gender = genderRepository.findById(0).get()
            it.isTaxableOk = false
        }

        val date = Date().apply {
            time = 0
        }
        val password = AuthUtil.generatePassword()
        val salt = AuthUtil.generateSalt()

        val authInfo = AuthInfo(
            employeeId,
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
        /* val employee = employeeRepository.findById(employeeId).orElse(null) ?: return false
         val roleList = arrayListOf<String>()
         for (labor in employee.laborList) {
             val roleId = labor.id?.roleId ?: continue
             roleList.add(roleId)
         }
             employee.roleId = "2"
             employeeRepository.save(employee)*/
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
        val employee = employeeRepository.findById(employeeId).orElse(null)
        val retire = Retirement()
        retire.id = employeeId
        retire.date = Date().toInstant()
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
        employeeId: String,
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

    fun getAuthority(
        employeeId: String
    ): Int? {
        val employeeLabor = employeeLaborRepository.findById_EmployeeId(employeeId)
        if (employeeLabor.isEmpty()) {
            return null
        }
        return roleRepository.findById(employeeLabor[0].id?.roleId ?: return null).orElse(null).authority?.id
    }
}
