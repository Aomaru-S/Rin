package com.rinats.rin.interceptor

import com.rinats.rin.annotation.NonAuth
import com.rinats.rin.repository.AuthInfoRepository
import com.rinats.rin.repository.EmployeeRepository
import org.springframework.core.annotation.AnnotationUtils
import org.springframework.web.method.HandlerMethod
import org.springframework.web.servlet.HandlerInterceptor
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

class AuthInterceptor(
    private val authInfoRepository: AuthInfoRepository,
    private val employeeRepository: EmployeeRepository
) : HandlerInterceptor {

    override fun preHandle(
        request: HttpServletRequest,
        response: HttpServletResponse,
        handler: Any
    ): Boolean {

        if (isNonAuth(handler)) {
            return true
        }

        val accessToken = request.getParameter("accessToken")
        if (!checkAccessToken(accessToken)) {
            response.status
            return false
        }

        val employeeId = authInfoRepository.findByAccessToken(accessToken).get().employeeId
        val employee = employeeRepository.findById(employeeId)
        request.setAttribute("employee", employee)
        return true
    }

    private fun isNonAuth(handler: Any): Boolean {
        val hm = try {
            HandlerMethod::class.java.cast(handler)
        } catch (e: ClassCastException) {
            return false
        }
        val method = hm.method
        val annotation = AnnotationUtils.findAnnotation(method, NonAuth::class.java)
        return annotation != null
    }

    private fun checkAccessToken(accessToken: String?): Boolean {
        accessToken ?: return false
        return authInfoRepository.existsByAccessToken(accessToken)
    }
}