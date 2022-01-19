package com.rinats.rin.controller.parttimejob

import com.rinats.rin.annotation.NonAuth
import com.rinats.rin.model.table.Employee
import com.rinats.rin.model.form.ChangePasswordForm
import com.rinats.rin.model.form.ForgetPasswordForm
import com.rinats.rin.repository.ForgetPasswordAccessTokenRepository
import com.rinats.rin.service.AuthInfoService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.validation.BindingResult
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("api/auth_info")
class AuthInfoRestController(
    @Autowired
    private val authInfoService: AuthInfoService,
    private val forgetPasswordAccessTokenRepository: ForgetPasswordAccessTokenRepository
) {
    @PostMapping("/password")
    fun changePassword(
        @RequestAttribute
        employee: Employee,
        @ModelAttribute @Validated
        changePasswordForm: ChangePasswordForm,
        bindingResult: BindingResult
    ): HashMap<String, Boolean> {
        if (bindingResult.hasErrors()) {
            System.err.println("hasError")
            System.err.println(changePasswordForm.oldPassword)
            System.err.println(changePasswordForm.newPassword1)
            return hashMapOf("result" to false)
        }
        val result = authInfoService.changePassword(
            employee.id,
            changePasswordForm
        )
        return hashMapOf("result" to result)
    }

    @NonAuth
    @PostMapping("/forget_password")
    fun forgetPassword(
        @Validated
        @ModelAttribute
        forgetPasswordForm: ForgetPasswordForm,
        bindingResult: BindingResult
    ): HashMap<String, Boolean> {
        if (bindingResult.hasErrors()) {
            return hashMapOf("result" to false)

        }
        val result = authInfoService.forgetPasswordMail(forgetPasswordForm.employeeId, forgetPasswordForm.mailAddress)
        return hashMapOf("result" to result)
    }
}