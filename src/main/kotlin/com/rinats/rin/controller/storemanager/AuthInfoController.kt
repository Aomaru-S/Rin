package com.rinats.rin.controller.storemanager

import com.rinats.rin.model.other.CompleteMessage
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping

@Controller
@RequestMapping("/auth_info")
class AuthInfoController {
    @GetMapping("/lockout")
    fun lockedOutList(): String {
        return "LockOutLift"
    }

    @PostMapping("/lockout")
    fun releaseLockout(
        model: Model
    ): String {
        val message = CompleteMessage("ロックアウト解除完了: Rin", "ロックアウトが解除されました。")
        model.addAttribute("message", message)
        return "complete"
    }
}