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
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.SessionAttribute
import javax.servlet.http.HttpServletResponse
import javax.servlet.http.HttpSession

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
    fun resetPasswordForm(
        session: HttpSession,
        response: HttpServletResponse,
        @RequestParam(required = false) uuid: String?
    ): String {
        if (uuid == null || uuid.isBlank()) {
            response.sendError(404)
        }
        val isExist = if (uuid != null) {
            forgetPasswordAccessTokenRepository.existsById(uuid)
        } else {
            false
        }
        if (!isExist) {
            response.sendError(404)
        }
        session.setAttribute("uuid", uuid)
        return "reset_password"
    }

    @NonAuth
    @PostMapping("/reset_password")
    fun resetPassword(
        @SessionAttribute
        uuid: String?,
        @RequestParam(name = "new_password")
        newPassword: String?
    ): String {
        if (uuid == null || uuid.isEmpty() || newPassword == null) {
            return "redirect:https://google.com"
        }
        authInfoService.resetPassword(uuid, newPassword)
        return "reset_password"
    }

    @GetMapping("change_password")
    fun changePasswordForm(): String{
        return "change_password"
    }
}