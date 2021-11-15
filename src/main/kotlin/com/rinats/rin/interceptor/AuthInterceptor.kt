package com.rinats.rin.interceptor

import com.rinats.rin.annotation.NonAuth
import com.rinats.rin.annotation.PartTimeJob
import com.rinats.rin.annotation.StoreManager
import com.rinats.rin.model.AuthInfo
import com.rinats.rin.model.Employee
import com.rinats.rin.repository.AuthInfoRepository
import com.rinats.rin.repository.EmployeeRepository
import org.springframework.core.annotation.AnnotationUtils
import org.springframework.web.method.HandlerMethod
import org.springframework.web.servlet.HandlerInterceptor
import java.lang.reflect.Method
import java.util.*
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
        val method = getMethod(handler) ?: return false

        if (checkAuthResource(method)) {
            return true
        }

        val accessToken = request.getParameter("access_token")
        val employeeId = authInfoRepository.findByAccessToken(accessToken).get().employeeId
        val employee = employeeRepository.findById(employeeId).get()

        if (!checkAccessToken(accessToken)) {
            println("accessToken")
            return false
        }
        if (!checkRole(employee, method)) {
            println("role")
            return false
        }

        if (!checkExpire(authInfoRepository.findByAccessToken(accessToken).get())) {
            println("expire")
            return false
        }

        request.setAttribute("employee", employee)
        return true
    }

    private fun getMethod(handler: Any): Method? {
        val hm = try {
            HandlerMethod::class.java.cast(handler)
        } catch (e: ClassCastException) {
            return null
        }
        return hm.method
    }

    private fun checkAuthResource(method: Method): Boolean {
        return AnnotationUtils.findAnnotation(method, NonAuth::class.java) == null
    }

    private fun checkAccessToken(accessToken: String?): Boolean {
        accessToken ?: return false
        return authInfoRepository.existsByAccessToken(accessToken)
    }

    private fun checkRole(employee: Employee, method: Method): Boolean {
        val hasStoreManager = AnnotationUtils.findAnnotation(method, StoreManager::class.java) != null
        val hasPartTimeJob = AnnotationUtils.findAnnotation(method, PartTimeJob::class.java) != null
        if ((hasStoreManager && hasPartTimeJob) ||
            !hasStoreManager && !hasPartTimeJob) {
            return true
        }

        return when(employee.roleId) {
            "1" -> {
                hasStoreManager
            }
            else -> {
                hasPartTimeJob
            }
        }
    }

    private fun checkExpire(authInfo: AuthInfo): Boolean {
        val today = Date()
        return authInfo.expire >= today
    }
}