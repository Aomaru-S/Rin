package com.rinats.rin.service

import com.rinats.rin.model.form.ChangePasswordForm
import com.rinats.rin.model.table.ForgetPasswordAccessToken
import com.rinats.rin.repository.AuthInfoRepository
import com.rinats.rin.repository.EmployeeSer
import com.rinats.rin.repository.ForgetPasswordAccessTokenRepository
import com.rinats.rin.util.AuthUtil
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.mail.MailSender
import org.springframework.mail.SimpleMailMessage
import org.springframework.stereotype.Service
import java.util.*

@Service
class AuthInfoService(
    @Autowired
    private val authInfoRepository: AuthInfoRepository,
    private val employeeRepository: EmployeeSer,
    private val forgetPasswordAccessTokenRepository: ForgetPasswordAccessTokenRepository,
    private val sender: MailSender
) {
    fun changePassword(employeeId: String?, changePasswordForm: ChangePasswordForm): Boolean {
        if (
            employeeId == null ||
            changePasswordForm.oldPassword == null ||
            changePasswordForm.newPassword1 == null ||
            changePasswordForm.newPassword2 == null ||
            changePasswordForm.newPassword1 != changePasswordForm.newPassword2
        ) {
            return false
        }

        val authInfo = authInfoRepository.findById(employeeId).orElse(null) ?: return false
        val oldDigest = AuthUtil.getDigest(changePasswordForm.oldPassword, authInfo.salt)
        if (authInfo.password != oldDigest) {
            return false
        }
        val newDigest = AuthUtil.getDigest(changePasswordForm.newPassword1, authInfo.salt)
        authInfo.password = newDigest
        authInfoRepository.save(authInfo)
        return true
    }

    fun forgetPasswordMail(employeeId: String?, mailAddress: String?): Boolean {
        if (employeeId == null || mailAddress == null) {
            return false
        }
        val employee = employeeRepository.findById(employeeId).orElse(null) ?: return false
        val tmp = employeeRepository.findByMailAddress(mailAddress).orElse(null) ?: return false
        val employeeId2 = tmp.id
        val uuid = UUID.randomUUID().toString()
        if (employee.id != employeeId2) {
            return false
        }
        sendForgetPasswordMail(employee.mailAddress ?: return false, uuid)

        val forgetPasswordAccessToken = ForgetPasswordAccessToken(
            uuid,
            employeeId
        )
        forgetPasswordAccessTokenRepository.save(forgetPasswordAccessToken)
        return true
    }

    fun resetPassword(
        uuid: String,
        newPassword: String
    ): Boolean {
        val fpa = forgetPasswordAccessTokenRepository.findById(uuid).orElse(null) ?: return false
        if (fpa.employeeId == null) {
            return false
        }
        val authInfo = authInfoRepository.findById(fpa.employeeId).orElse(null) ?: return false
        val newSalt = AuthUtil.generateSalt()
        val newDigest = AuthUtil.getDigest(newPassword, newSalt)

        authInfo.salt = newSalt
        authInfo.password = newDigest

        return true
    }

    private fun sendForgetPasswordMail(
        mailAddress: String,
        uuid: String
    ) {
        val message = SimpleMailMessage()
        message.setFrom("info@rin-ats.com")
        message.setTo(mailAddress)
        message.setSubject("パスワード再設定URLのお知らせ")
        message.setText(
            "Rinシステムのパスワード再設定をするには、以下のリンクを踏んでください。\n" +
                    "http://localhost/reset_password?uuid=$uuid"
        )
        sender.send(message)
    }
}