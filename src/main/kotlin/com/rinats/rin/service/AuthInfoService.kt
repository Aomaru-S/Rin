package com.rinats.rin.service

import com.rinats.rin.model.AuthInfo
import com.rinats.rin.model.ForgetPasswordAccessToken
import com.rinats.rin.repository.AuthInfoRepository
import com.rinats.rin.repository.EmployeeRepository
import com.rinats.rin.repository.ForgetPasswordAccessTokenRepository
import com.rinats.rin.util.AuthUtil
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.mail.MailSender
import org.springframework.mail.SimpleMailMessage
import org.springframework.stereotype.Service
import java.util.*
import kotlin.collections.HashMap

@Service
class AuthInfoService(
    @Autowired
    private val authInfoRepository: AuthInfoRepository,
    private val employeeRepository: EmployeeRepository,
    private val forgetPasswordAccessTokenRepository: ForgetPasswordAccessTokenRepository,
    private val sender: MailSender
) {
    fun changePassword(employeeId: String, oldPassword: String, newPassword: String): Boolean{
        val authInfo = authInfoRepository.findById(employeeId).orElse(null) ?: return false
        val oldDigest = AuthUtil.getDigest(oldPassword, authInfo.salt)
        if (authInfo.password != oldDigest) {
            return false
        }
        val newDigest = AuthUtil.getDigest(newPassword, authInfo.salt)
        authInfo.password = newDigest
        authInfoRepository.save(authInfo)
        return true
    }

    fun forgetPasswordMail(employeeId: String, mailAddress: String): Boolean {
        val employee = employeeRepository.findById(employeeId).orElse(null) ?: return false
        val tmp = employeeRepository.findByMailAddress(mailAddress).orElse(null) ?: return false
        val employeeId2 = tmp.employeeId
        val uuid = UUID.randomUUID().toString()
        if (employee.employeeId != employeeId2) {
            return false
        }
        sendForgetPasswordMail(employee.mailAddress, uuid)

        val forgetPasswordAccessToken = ForgetPasswordAccessToken(
            employeeId,
            uuid
        )
        forgetPasswordAccessTokenRepository.save(forgetPasswordAccessToken)
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
            "Rinシステムのパスワード再設定をするには、以下のリンクを踏んでください。" +
                    "http://localhost/forgetPassword?uuid=$uuid"
        )
        sender.send(message)
    }
}