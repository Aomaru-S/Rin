package com.rinats.rin.service

import com.rinats.rin.repository.AuthInfoRepository
import org.apache.commons.codec.digest.DigestUtils
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import java.util.*


@Service
class AuthService(
    @Autowired
    private val authInfoRepository: AuthInfoRepository
) {
    fun loginWithGetAccessToken(userId: String, _password: String): String? {
        val authInfo = authInfoRepository.findById(userId).orElse(null) ?: return null
        val digest = DigestUtils.sha512Hex(_password + authInfo.salt)

        if (authInfo.password != digest) {
            return null
        }

        val accessToken = UUID.randomUUID().toString()
        authInfo.accessToken = accessToken

        val cExpire = Calendar.getInstance()
        cExpire.add(Calendar.MONTH, 1)
        authInfo.expire = cExpire.time

        authInfoRepository.save(authInfo)
        return accessToken
    }

    fun checkAccessToken(accessToken: String): Boolean {
        if (authInfoRepository.existsByAccessToken(accessToken)) {
            return authInfoRepository.findByAccessToken(accessToken).get().expire >= Date()
        }
        return false
    }

    fun logout(accessToken: String) {
        val authInfo = authInfoRepository.findByAccessToken(accessToken).get()
        authInfo.expire.time = 0
        authInfoRepository.save(authInfo)
    }
}