package com.rinats.rin.service

import com.rinats.rin.model.form.AddEmployeeForm
import com.rinats.rin.model.form.UpdateEmployeeForm
import com.rinats.rin.model.table.*
import com.rinats.rin.model.table.compositeId.EmployeeLaborId
import com.rinats.rin.repository.*
import com.rinats.rin.util.AuthUtil
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.mail.MailException
import org.springframework.mail.MailSender
import org.springframework.mail.SimpleMailMessage
import org.springframework.stereotype.Service
import java.time.Instant
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
    private val genderRepository: GenderRepository,
    private val mailAddressAuthRepository: MailAddressAuthRepository
) {
    //    従業員仮登録処理
    fun addTentativeEmployee(addEmployeeForm: AddEmployeeForm): Boolean {
        val employeeId = getAndUpdateSequence().toString()

        val labor = EmployeeLabor()
        val employeeLaborId = EmployeeLaborId()
        employeeLaborId.employeeId = employeeId
        employeeLaborId.roleId = 2
        labor.id = employeeLaborId
        labor.labor = 2
        val laborList = arrayListOf<EmployeeLabor>()
        laborList.add(labor)
        val employee = createEmployeeTableFromForm(employeeId, addEmployeeForm)

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
        try {
            sendMail(addEmployeeForm, employeeId, password)
        } catch (e: MailException) {
            return false
        }
        employeeRepository.save(employee ?: return false)
        authInfoRepository.save(authInfo)
        return true
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
    fun getEmployee(employeeId: String?): Employee? {
        return employeeRepository.findById(employeeId ?: return null).orElse(null)
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

    fun sendAuthMail(
        mailAddress: String,
        uuid: String
    ) {
        val message = SimpleMailMessage()
        message.setFrom("info@rin-ats.com")
        message.setTo(mailAddress)
        message.setSubject("メールアドレス認証")
        message.setText("http://localhost/auth_mail_address?uuid=$uuid")
        sender.send(message)
    }

    fun changeMailAddress(employeeId: String?, mailAddress: String?): Boolean {
        if (
            employeeId == null ||
            mailAddress == null
        ) {
            return false
        }
        val employee = employeeRepository.findById(employeeId).orElse(null) ?: return false
        employee.mailAddress = mailAddress
        employeeRepository.save(employee)
        return true
    }

    fun changeMailAddress(uuid: String?): Boolean {
        uuid ?: return false
        val mailAddressAuth = getNewMailAddressFromUuid(uuid)
        if ((mailAddressAuth?.expire ?: return false) <= Instant.now()) {
            return false
        }
        val employeeId = mailAddressAuth?.id ?: return false
        val employee = employeeRepository.findById(employeeId).orElse(null) ?: return false
        employee.mailAddress = mailAddressAuth.mailAddress
        employeeRepository.save(employee)
        mailAddressAuthRepository.deleteById(employeeId)
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
        try {
            sender.send(message)
        } catch (e: MailException) {
            throw e
        }
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

    fun getNewMailAddressFromUuid(uuid: String): MailAddressAuth? {
        val authMailAddressList = mailAddressAuthRepository.findByUuid(uuid)
        if (authMailAddressList.isEmpty()) {
            return null
        }
        return authMailAddressList[0]
    }

    fun updateEmployee(employeeId: String?, updateEmployeeForm: UpdateEmployeeForm): Boolean {
        val isExists = employeeRepository.existsById(employeeId ?: return false)
        if (!isExists) return false
        val employee = createEmployeeTableFromForm(employeeId, updateEmployeeForm) ?: return false
        employeeRepository.save(employee)
        return true
    }

    private fun createEmployeeTableFromForm(employeeId: String?, addEmployeeForm: AddEmployeeForm): Employee? {
        return Employee().also {
            it.id = employeeId
            it.firstName = addEmployeeForm.firstName
            it.lastName = addEmployeeForm.lastName
            it.birthday = addEmployeeForm.birthday
            it.hourlyWage = addEmployeeForm.hourlyWage
            it.isAndroidNotification = false
            it.mailAddress = addEmployeeForm.mailAddress
            it.isTentative = true
            it.gender = genderRepository.findById(addEmployeeForm.genderId ?: return null).orElse(null) ?: return null
            it.isTaxableOk = addEmployeeForm.isTaxable
        }
    }

    private fun createEmployeeTableFromForm(employeeId: String?, updateEmployeeForm: UpdateEmployeeForm): Employee? {
        return Employee().also {
            it.id = employeeId
            it.firstName = updateEmployeeForm.firstName
            it.lastName = updateEmployeeForm.lastName
            it.birthday = updateEmployeeForm.birthday
            it.hourlyWage = updateEmployeeForm.hourlyWage
            it.isAndroidNotification = updateEmployeeForm.isAndroidNotification
            it.mailAddress = updateEmployeeForm.mailAddress
            it.isTentative = updateEmployeeForm.isTentative
            it.gender = genderRepository.findById(updateEmployeeForm.genderId ?: return null).orElse(null) ?: return null
            it.isTaxableOk = updateEmployeeForm.isTaxable
        }
    }
}
