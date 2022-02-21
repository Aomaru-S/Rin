package com.rinats.rin.controller.storemanager

import com.rinats.rin.annotation.StoreManager
import com.rinats.rin.model.form.ChangeMailAddressForm
import com.rinats.rin.model.form.ChangePasswordForm
import com.rinats.rin.model.other.CompleteMessage
import com.rinats.rin.model.table.Employee
import com.rinats.rin.repository.MailAddressAuthRepository
import com.rinats.rin.service.AuthInfoService
import com.rinats.rin.service.EmployeeService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.validation.BindingResult
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.*


@Controller
class StoreManagerController(
    @Autowired
    private val authInfoService: AuthInfoService,
    private val employeeService: EmployeeService,
    private val mailAddressAuthRepository: MailAddressAuthRepository
) {
    @StoreManager
    @GetMapping("/store_manager_top")
    fun storeManagerTop(): String {
        return "store_manager_index"
    }

    @StoreManager
    @GetMapping("/setting")
    fun setting(): String {
        return "setting"
    }

    @StoreManager
    @GetMapping("/change_password")
    fun changePasswordForm(model: Model): String {
        val changePasswordForm = ChangePasswordForm()
        model.addAttribute("changePasswordForm", changePasswordForm)
        return "change_password"
    }

    @StoreManager
    @PostMapping("/change_password")
    fun changePassword(
        @ModelAttribute
        changePasswordForm: ChangePasswordForm,
        @RequestAttribute
        employee: Employee,
        model: Model
    ): String {
        employee.id ?: return "redirect:/change_password?error"
        return when (authInfoService.changePassword(employee.id, changePasswordForm)) {
            true -> {
                val message = CompleteMessage("パスワード変更完了: Rin", "パスワード変更が変更されました")
                model.addAttribute("message", message)
                return "complete"
            }
            false -> "redirect:/change_password?error"
        }
    }

    @StoreManager
    @GetMapping("/change_mail_address")
    fun changeMailAddressForm(
        model: Model,
        @RequestAttribute
        employee: Employee
    ): String {
        val changeMailAddressForm = ChangeMailAddressForm().also {
            it.mailAddress = employee.mailAddress
        }
        model.addAttribute("changeMailAddressForm", changeMailAddressForm)
        return "change_mail_address"
    }

    @PostMapping("/auth_mail_address")
    fun sendAuthMail(
        @RequestAttribute employee: Employee,
        @Validated @ModelAttribute changeMailAddressForm: ChangeMailAddressForm,
        bindingResult: BindingResult,
        model: Model
    ): String {
        if (bindingResult.hasErrors()) {
            return "redirect:/change_mail_address?error"
        }
        val result = employeeService.changeMailAddress(employee.id ?: "", changeMailAddressForm.mailAddress)

        val message = CompleteMessage("メールアドレス変更完了: Rin", "メールアドレスが変更されました")
        model.addAttribute("message", message)
        return "complete"
    }

    @GetMapping("/auth_mail_address")
    fun changeMailAddress(
        @RequestAttribute employee: Employee,
        @RequestParam(required = false) uuid: String? = null,
        model: Model
    ): String {
        return when (employeeService.changeMailAddress(uuid)) {
            true -> {
                val message = CompleteMessage("メールアドレス変更完了: Rin", "メールアドレスが変更されました")
                model.addAttribute("message", message)
                "complete"
            }
            false -> {
                val message = CompleteMessage(
                    "エラー",
                    "無効なURlです"
                )
                model.addAttribute("error", message)
                "redirect:/change_mail_address?error"
            }
        }
    }
}