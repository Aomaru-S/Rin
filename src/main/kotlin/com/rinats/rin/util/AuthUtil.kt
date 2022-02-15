package com.rinats.rin.util

import org.apache.commons.codec.digest.DigestUtils
import org.passay.CharacterRule
import org.passay.EnglishCharacterData
import org.passay.PasswordGenerator


class AuthUtil {

    companion object {
        private val rules: List<CharacterRule> = listOf(
            CharacterRule(EnglishCharacterData.UpperCase, 1),
            CharacterRule(EnglishCharacterData.LowerCase, 1),
            CharacterRule(EnglishCharacterData.Digit, 1)
        )

        fun generatePassword(): String {
            val generator = PasswordGenerator()
//            val password = generator.generatePassword(8, rules)
            val password = "password"
            println(password)
            return password
        }

        fun generateSalt(): String {
            return (1..9999).random().toString().padStart(4, '0')
        }

        fun getDigest(password: String, salt: String): String {
            return DigestUtils.sha512Hex(password + salt)
        }
    }
}