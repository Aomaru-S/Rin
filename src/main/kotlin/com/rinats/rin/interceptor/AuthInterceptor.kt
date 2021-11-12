package com.rinats.rin.interceptor

import com.rinats.rin.repository.AuthInfoRepository
import com.rinats.rin.repository.EmployeeRepository
import org.springframework.web.servlet.HandlerInterceptor
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

class AuthInterceptor(
    private val authInfoRepository: AuthInfoRepository,
    private val employeeRepository: EmployeeRepository
) : HandlerInterceptor {

    override fun preHandle(request: HttpServletRequest,
                           response: HttpServletResponse,
                           handler: Any): Boolean {
        val accessToken = request.getParameter("accessToken") ?: return false
        if(!authInfoRepository.existsByAccessToken(accessToken)) {
            return false
        }
        val employeeId = authInfoRepository.findByAccessToken(accessToken).get().employeeId
        val employee = employeeRepository.findById(employeeId)
        request.setAttribute("employee", employee)
        return true
    }
}