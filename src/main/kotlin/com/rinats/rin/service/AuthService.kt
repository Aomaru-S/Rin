package com.rinats.rin.service

import com.rinats.rin.repository.AuthInfoRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import java.util.*

@Service
class AuthService(
    @Autowired
    private val authInfoRepository: AuthInfoRepository
) {
    fun loginWithGetAccessToken(userId: String, _password: String): String? {
        val password = authInfoRepository.findById(userId).orElse(null) ?: return null
        if (password.password != _password) {
            return null
        }

        val accessToken = UUID.randomUUID().toString()
        password.accessToken = accessToken

        val cExpire = Calendar.getInstance()
        cExpire.add(Calendar.MONTH, 1)
        password.expire = cExpire.time

        authInfoRepository.save(password)
        return accessToken
    }
}