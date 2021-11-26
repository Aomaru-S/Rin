package com.rinats.rin.interceptor

import com.rinats.rin.annotation.NonAuth
import com.rinats.rin.annotation.PartTimeJob
import com.rinats.rin.annotation.StoreManager
import com.rinats.rin.annotation.TentativeEmployee
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
        val method = getMethod(handler, response) ?: return false

        if (checkAuthResource(method)) {
            return true
        }

        var accessToken: String? = null

        when(request.requestURI.startsWith("/api/v1")) {
            true -> {
                accessToken = request.getHeader("Authorization") ?: return false
            }
            false -> {
                var at: String? = null
                request.cookies?.forEach { cookie ->
                    if (cookie.name == "access_token") {
                        at = cookie.value
                    }
                }
                accessToken = at
                if (accessToken == null) {
                    response.sendRedirect("/login")
                    return true
                }
            }
        }

        val employeeId = authInfoRepository.findByAccessToken(accessToken).get().employeeId
        val employee = employeeRepository.findById(employeeId).get()

        if (!checkAccessToken(accessToken)) {
            response.sendError(401)
            return false
        }
        if (!checkRole(employee, method)) {
            response.sendError(403)
            return false
        }

        if (!checkExpire(authInfoRepository.findByAccessToken(accessToken).get())) {
            response.sendError(401)
            return false
        }

        request.setAttribute("employee", employee)
        request.setAttribute("access_token", accessToken)
        return true
    }

    private fun getMethod(handler: Any, response: HttpServletResponse): Method? {
        val hm = try {
            HandlerMethod::class.java.cast(handler)
        } catch (e: ClassCastException) {
            response.sendError(404)
            return null
        }
        return hm.method
    }

    private fun checkAuthResource(method: Method): Boolean {
        return AnnotationUtils.findAnnotation(method, NonAuth::class.java) != null
    }

    private fun checkAccessToken(accessToken: String?): Boolean {
        accessToken ?: return false
        return authInfoRepository.existsByAccessToken(accessToken)
    }

    private fun checkRole(employee: Employee, method: Method): Boolean {
        val hasStoreManager = AnnotationUtils.findAnnotation(method, StoreManager::class.java) != null
        val hasPartTimeJob = AnnotationUtils.findAnnotation(method, PartTimeJob::class.java) != null
        val hasTentativeEmployee = AnnotationUtils.findAnnotation(method, TentativeEmployee::class.java) != null

        val roleId = employee.roleId
        if (hasStoreManager && roleId == "1") {
            return true
        }
        if (hasPartTimeJob && roleId == "2") {
            return true
        }
        if (hasTentativeEmployee && roleId == "3") {
            return true
        }

        if(!(hasStoreManager || hasPartTimeJob || hasTentativeEmployee)) {
            return true
        }

        return false
    }

    private fun checkExpire(authInfo: AuthInfo): Boolean {
        val today = Date()
        return authInfo.expire >= today
    }
}