package com.rinats.rin.service

import com.rinats.rin.repository.AuthInfoRepository
import com.rinats.rin.util.AuthUtil
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class AuthInfoService(
    @Autowired
    private val authInfoRepository: AuthInfoRepository
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
}