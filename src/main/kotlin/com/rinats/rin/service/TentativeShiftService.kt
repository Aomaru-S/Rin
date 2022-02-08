package com.rinats.rin.service

import com.rinats.rin.model.table.Shift
import com.rinats.rin.model.table.TentativeShift
import com.rinats.rin.repository.ShiftRepository
import com.rinats.rin.repository.TentativeShiftDetailRepository
import com.rinats.rin.repository.TentativeShiftRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import java.time.ZoneId
import java.util.*

@Service
class TentativeShiftService(
    @Autowired
    private val tentativeShiftRepository: TentativeShiftRepository,
    private val shiftRepository: ShiftRepository
) {

    fun getTentativeShiftList(): List<TentativeShift> = tentativeShiftRepository.findAll()

    fun submitTentativeShift() {
        val tentativeShiftList = tentativeShiftRepository.findAll()
        tentativeShiftList.forEach {
            val shift = Shift(
                Date.from(it.id?.shiftDate?.atStartOfDay(ZoneId.systemDefault())?.toInstant()),
                it?.id?.employee?.id ?: return@forEach
            )
            shiftRepository.save(shift)
        }
    }
}