package com.rinats.rin.interceptor

import com.rinats.rin.annotation.NonAuth
import com.rinats.rin.annotation.PartTimeJob
import com.rinats.rin.annotation.StoreManager
import com.rinats.rin.annotation.TentativeEmployee
import com.rinats.rin.model.table.AuthInfo
import com.rinats.rin.model.table.Employee
import com.rinats.rin.repository.AuthInfoRepository
import com.rinats.rin.repository.EmployeeLaborRepository
import com.rinats.rin.repository.EmployeeRepository
import com.rinats.rin.service.EmployeeService
import org.springframework.core.annotation.AnnotationUtils
import org.springframework.http.HttpMethod
import org.springframework.web.method.HandlerMethod
import org.springframework.web.servlet.HandlerInterceptor
import java.lang.reflect.Method
import java.util.*
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

class AuthInterceptor(
    private val authInfoRepository: AuthInfoRepository,
    private val employeeRepository: EmployeeRepository,
    private val employeeService: EmployeeService,
    private val employeeLaborRepository: EmployeeLaborRepository
) : HandlerInterceptor {

    override fun preHandle(
        request: HttpServletRequest,
        response: HttpServletResponse,
        handler: Any
    ): Boolean {
        val isCss = request.requestURI.startsWith("/css")
        if (isCss) {
            return true
        }

        if (HttpMethod.OPTIONS.matches(request.method)) {
            return true
        }

        val status = response.status
        if (status == 404) {
            response.sendError(404)
            return true
        }
        if (status / 100 == 4 || status / 100 == 5) {
            response.sendError(status)
            return false
        }

        val method = getMethod(handler, response)
        if (method == null) {
            response.sendRedirect("/login")
            return false
//            return true
        }

        if (checkAuthResource(method)) {
            return true
        }

        var accessToken: String? = null

        val isApi = request.requestURI.startsWith("/api")

        when (isApi) {
            true -> {
                accessToken = request.getHeader("Authorization")
                if (accessToken == null) {
                    response.sendError(401)
                    return false
                }
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
                    return false
                }
            }
        }

        val employeeAuthInfo = authInfoRepository.findByAccessToken(accessToken).orElse(null)
        if (employeeAuthInfo == null) {
            response.sendRedirect("/login")
            return false
        }
        val employee = employeeRepository.findById(employeeAuthInfo.employeeId).orElse(null)
        if (employee == null) {
            response.sendRedirect("/login")
            return false
        }

        if (!checkAccessToken(accessToken)) {
            if (!isApi) {
                response.sendRedirect("/login")
                return false
            }
            response.sendError(401)
            return false
        }
        if (!checkRole(employee, method)) {
            response.sendError(403)
            return false
        }

        if (!checkExpire(authInfoRepository.findByAccessToken(accessToken).get())) {
            if (!isApi) {
                response.sendRedirect("/login")
                return false
            }
            response.sendError(401)
            return false
        }

        request.setAttribute("is_api", isApi)
        request.setAttribute("employee", employee)
        request.setAttribute("access_token", accessToken)
        return true
    }

    private fun getMethod(handler: Any, response: HttpServletResponse): Method? {
        val hm = try {
            HandlerMethod::class.java.cast(handler)
        } catch (e: ClassCastException) {
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

        if (hasStoreManager && employeeService.getAuthority(employee.id ?: "") == 0) {
            return true
        }
        if (hasPartTimeJob && employeeService.getAuthority(employee.id ?: "") == 1) {
            return true
        }

        if (!(hasStoreManager || hasPartTimeJob || hasTentativeEmployee)) {
            return true
        }

        return false
    }

    private fun checkExpire(authInfo: AuthInfo): Boolean {
        val today = Date()
        return authInfo.expire >= today
    }
}