package com.rinats.rin.repository

import com.rinats.rin.model.Reservation
import org.springframework.data.jpa.repository.JpaRepository

interface ReservationRepository : JpaRepository<Reservation, String> {

    fun findByOrderByIdAsc(): List<Reservation>
}