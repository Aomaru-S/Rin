package com.rinats.rin.controller.common

import com.rinats.rin.annotation.NonAuth
import com.rinats.rin.model.form.ForgetPasswordForm
import com.rinats.rin.repository.EmployeeRepository
import com.rinats.rin.repository.ForgetPasswordAccessTokenRepository
import com.rinats.rin.service.AuthInfoService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.validation.BindingResult
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.ModelAttribute
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestParam
import javax.servlet.http.HttpServletResponse

@Controller
class AuthInfoController(
    @Autowired
    private val authInfoService: AuthInfoService,
    private val forgetPasswordAccessTokenRepository: ForgetPasswordAccessTokenRepository,
    private val employeeRepository: EmployeeRepository
) {
    @NonAuth
    @GetMapping("/forget_password")
    fun forgetPasswordForm(model: Model): String {
        model.addAttribute("forgetPasswordForm", ForgetPasswordForm())
        return "forget_password"
    }

    @NonAuth
    @PostMapping("/forget_password")
    fun forgetPassword(
        @Validated
        forgetPasswordForm: ForgetPasswordForm,
        bindingResult: BindingResult
    ): String {
        if (bindingResult.hasErrors()) {
            return "redirect:/forget_password?error"
        }
        val result = authInfoService.forgetPasswordMail(
            forgetPasswordForm.employeeId,
            forgetPasswordForm.mailAddress
        )
        if (result) {
            return "redirect:/mail_send_completion"
        }
        return "redirect:/forget_password?error"
    }

    @NonAuth
    @GetMapping("/mail_send_completion")
    fun mailSendCompletion(): String {
        return "mail_send_completion"
    }

    @NonAuth
    @GetMapping("/reset_password")
    fun resetPassword(
        response: HttpServletResponse,
        @RequestParam(required = false) uuid: String?
    ): String {
        if (uuid == null || uuid.isBlank()) {
            return "redirect:https://google.com"
        }
        val isExists = forgetPasswordAccessTokenRepository.existsById(uuid)
        if (isExists) {
            response.sendError(HttpServletResponse.SC_NOT_FOUND)
        }

        return "setei"
    }
}