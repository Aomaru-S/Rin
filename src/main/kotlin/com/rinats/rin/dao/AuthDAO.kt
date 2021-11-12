package com.rinats.rin.dao

import com.rinats.rin.repository.PasswordRepository

class AuthDAO(
    private val passwordRepository: PasswordRepository
) {
    fun login(userId: String, _password: String): Boolean {
        val password = passwordRepository.findById(userId).orElse(null) ?: return false
        if (password.password == _password) {
            return true
        }
        return false
    }
}