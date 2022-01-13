package com.rinats.rin

import com.rinats.rin.interceptor.AuthInterceptor
import com.rinats.rin.repository.AuthInfoRepository
import com.rinats.rin.repository.EmployeeRepository
import com.rinats.rin.service.EmployeeService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.web.servlet.config.annotation.InterceptorRegistry
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer


@Configuration
class WebMvcConfig(
    @Autowired
    private val authInfoRepository: AuthInfoRepository,
    private val employeeRepository: EmployeeRepository,
    private val employeeService: EmployeeService
) : WebMvcConfigurer{
    @Bean
    fun authorizationHandlerInterceptor(): AuthInterceptor? {
        return AuthInterceptor(authInfoRepository, employeeRepository, employeeService)
    }

    override fun addInterceptors(registry: InterceptorRegistry) {
        registry.addInterceptor(AuthInterceptor(authInfoRepository, employeeRepository, employeeService))
            .addPathPatterns("/**")
    }
}