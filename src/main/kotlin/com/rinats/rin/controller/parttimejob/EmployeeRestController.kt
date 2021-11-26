package com.rinats.rin.controller.parttimejob

import com.rinats.rin.model.Employee
import com.rinats.rin.model.form.ChangePasswordForm
import com.rinats.rin.repository.AuthInfoRepository
import com.rinats.rin.service.AuthInfoService
import com.rinats.rin.service.EmployeeService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.validation.BindingResult
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.*
import javax.validation.constraints.NotBlank

@RestController
@RequestMapping("api/v1/employee")
class EmployeeRestController(
    @Autowired
    private val employeeService: EmployeeService,
    private val authInfoService: AuthInfoService
) {
    @PostMapping("/change_mailAddress")
    fun changeMailAddress(
        @RequestAttribute employee: Employee,
        @Validated mailAddress: String
    ): HashMap<String, Boolean> {
        val result = employeeService.changeMailAddress(employee.employeeId, mailAddress)
        return hashMapOf("result" to result)
    }

    @PostMapping("/change_password")
    fun changePassword(
        @RequestAttribute employee: Employee,
        @Validated
        changePasswordForm: ChangePasswordForm,
        bindingResult: BindingResult
    ): HashMap<String, Boolean> {
        if (bindingResult.hasErrors()) {
            return hashMapOf("result" to false)
        }
        val result = authInfoService.changePassword(
            employee.employeeId,
            changePasswordForm.oldPassword,
            changePasswordForm.newPassword
        )
        return hashMapOf("result" to result)
    }
}