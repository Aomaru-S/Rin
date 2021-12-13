package com.rinats.rin.controller.storemanager

import com.rinats.rin.annotation.StoreManager
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.GetMapping

@Controller
class StoreManagerController {
    @StoreManager
    @GetMapping("/store_manager_top")
    fun storeManagerTop(): String {
        return "/store_manager_top"
    }

    @StoreManager
    @GetMapping("/setting")
    fun setting(): String {
        return "setting"
    }
}