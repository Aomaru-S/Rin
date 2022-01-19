package com.rinats.rin.repository

import com.rinats.rin.model.table.Reservation
import org.springframework.data.jpa.repository.JpaRepository

interface ReservationRepository : JpaRepository<Reservation, String>