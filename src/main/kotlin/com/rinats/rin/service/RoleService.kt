package com.rinats.rin.service

import com.rinats.rin.model.table.Role
import com.rinats.rin.repository.RoleRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class RoleService(
    @Autowired
    private val roleRepository: RoleRepository
) {
    fun getRoleList(): List<Role> {
        return roleRepository.findAll()
            .filter { it.roleName.equals("store_manager").not() }
            .filter { it.roleName.equals("store_terminal").not() }
    }
}