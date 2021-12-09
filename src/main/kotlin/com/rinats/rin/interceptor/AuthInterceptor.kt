package com.rinats.rin.interceptor

import com.rinats.rin.annotation.NonAuth
import com.rinats.rin.annotation.PartTimeJob
import com.rinats.rin.annotation.StoreManager
import com.rinats.rin.annotation.TentativeEmployee
import com.rinats.rin.model.AuthInfo
import com.rinats.rin.model.Employee
import com.rinats.rin.model.LaborId
import com.rinats.rin.repository.AuthInfoRepository
import com.rinats.rin.repository.EmployeeRepository
import com.rinats.rin.repository.LaborRepository
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
    private val employeeRepository: EmployeeRepository
) : HandlerInterceptor {

    override fun preHandle(
        request: HttpServletRequest,
        response: HttpServletResponse,
        handler: Any
    ): Boolean {
        println("Status: ${response.status}")
        if (HttpMethod.OPTIONS.matches(request.method)) {
            System.err.println("option ok")
            return true
        }

        val status = response.status
        if (status == 404) {
            System.err.println("404 ng")
            response.sendError(404)
            return false
        }
        if (status / 100 == 4 || status / 100 == 5) {
            System.err.println("error ng")
            response.sendError(status)
            return false
        }

        val method = getMethod(handler, response)
        if (method == null) {
            System.err.println("method null ng")
            return false
        }

        if (checkAuthResource(method)) {
            println("NonNull ok")
            return true
        }

        var accessToken: String? = null

        val isApi = request.requestURI.startsWith("/api")

        when(isApi) {
            true -> {
                accessToken = request.getHeader("Authorization")
                if (accessToken == null) {
                    System.err.println("accessToken null ng1")
                    response.sendError(401)
                    return false
                }
            }
            false -> {
                var at: String? = null
                request.cookies?.forEach { cookie ->
                    if (cookie.name == "access_token") {
                        println("has cookie")
                        at = cookie.value
                    }
                }
                accessToken = at
                if (accessToken == null) {
                    System.err.println("accessToken null ng2")
                    response.sendRedirect("/login")
                    return false
                }
            }
        }

        val employeeId = authInfoRepository.findByAccessToken(accessToken).get().employeeId
        val employee = employeeRepository.findById(employeeId).get()

        if (!checkAccessToken(accessToken)) {
            if (!isApi) {
                System.err.println("invalid accessToken ng1")
                response.sendRedirect("/login")
                return false
            }
            response.sendError(401)
            System.err.println("invalid accessToken ng2")
            return false
        }
        if (!checkRole(employee, method)) {
            System.err.println("invalid role ng")
            response.sendError(403)
            return false
        }

        if (!checkExpire(authInfoRepository.findByAccessToken(accessToken).get())) {
            if (!isApi) {
                System.err.println("expire ng1")
                response.sendRedirect("/login")
                return false
            }
            System.err.println("expire ng2")
            response.sendError(401)
            return false
        }

        request.setAttribute("employee", employee)
        request.setAttribute("access_token", accessToken)
        println("auth ok")
        return true
    }

    private fun getMethod(handler: Any, response: HttpServletResponse): Method? {
        val hm = try {
            HandlerMethod::class.java.cast(handler)
        } catch (e: ClassCastException) {
            println("ClassCastException")
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

        if (hasStoreManager && employee.roleId == "1") {
            return true
        }
        if (hasPartTimeJob && employee.roleId == "2") {
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