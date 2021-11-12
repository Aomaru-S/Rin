package com.rinats.rin.service

import com.rinats.rin.repository.PasswordRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import java.util.*

@Service
class AuthService(
    @Autowired
    private val passwordRepository: PasswordRepository
) {
    fun loginWithGetAccessToken(userId: String, _password: String): UUID? {
        val password = passwordRepository.findById(userId).orElse(null) ?: return null
        if (password.password != _password) {
            return null
        }
        val accessToken = UUID.randomUUID()
//        password.accessToken = accessToken
        return accessToken
    }
}