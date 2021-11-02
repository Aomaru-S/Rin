package com.rinats.rin.model

import javax.persistence.*

@Entity
@Table(name = "role")
data class Role (
    @Id
    @Column(name = "role_id")
    var roleId: String,
    @Column(name = "role_name")
    var roleName: String,
    @OneToMany(fetch = FetchType.EAGER, cascade = [CascadeType.ALL])
    @JoinColumn(name = "role_id")
    var employeeList: List<Employee> = ArrayList()
)