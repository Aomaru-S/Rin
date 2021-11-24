package com.rinats.rin.service

import com.rinats.rin.repository.AuthInfoRepository
import com.rinats.rin.util.AuthUtil
import org.apache.commons.codec.digest.DigestUtils
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import java.util.*


@Service
class AuthService(
    @Autowired
    private val authInfoRepository: AuthInfoRepository
) {

// ログイン処理
    fun login(userId: String?, _password: String?): String? {
        if (userId == null || _password == null) {
            return null
        }
        val authInfo = authInfoRepository.findById(userId).orElse(null) ?: return null
        val digest = AuthUtil.getDigest(_password, authInfo.salt)

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

//    アクセストークンの有効性確認処理
    fun checkAccessToken(accessToken: String): Boolean {
        if (authInfoRepository.existsByAccessToken(accessToken)) {
            return authInfoRepository.findByAccessToken(accessToken).get().expire >= Date()
        }
        return false
    }

//    ログアウト処理
    fun logout(accessToken: String) {
        val authInfo = authInfoRepository.findByAccessToken(accessToken).get()
        authInfo.expire.time = 0
        authInfoRepository.save(authInfo)
    }
}