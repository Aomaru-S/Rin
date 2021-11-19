package com.rinats.rin.controller.common

import com.rinats.rin.annotation.NonAuth
import com.rinats.rin.model.form.AuthForm
import com.rinats.rin.service.AuthService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.validation.BindingResult
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.ModelAttribute
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import javax.servlet.http.Cookie
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

@Controller
class AuthController(
    @Autowired
    val authService: AuthService
) {
    @NonAuth
    @RequestMapping("/login")
    fun authForm(model: Model): String {
        model.addAttribute("authForm", AuthForm())
        return "login"
    }

    @NonAuth
    @PostMapping("/perform_login")
    fun login(
        @ModelAttribute @Validated authForm: AuthForm,
        result: BindingResult,
        response: HttpServletResponse
    ): String {
        if (result.hasErrors()) {
            return "redirect:login?error"
        }
        val accessToken = authService.login(authForm.employeeId, authForm.password)
            ?: return "redirect:login?error"
        val cookie = Cookie("access_token", accessToken)
        response.addCookie(cookie)
        return "redirect:/"
    }

    @GetMapping("/logout")
    fun performLogout(request: HttpServletRequest): String {
        authService.logout(request.getAttribute("access_token") as String)
        return "redirect:/login?logout"
    }
}