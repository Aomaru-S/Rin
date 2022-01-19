package com.rinats.rin.model.table

import com.alias.kh.shiftgenerator.model.compositeKey.TentativeShiftDetailId
import javax.persistence.Column
import javax.persistence.EmbeddedId
import javax.persistence.Entity
import javax.persistence.Table

@Entity
@Table(name = "tentative_shift_detail")
open class TentativeShiftDetail(
    tentativeShiftDetailId: TentativeShiftDetailId,
    laborInsufficient: Boolean,
    numOfPeopleInsufficient: Boolean
) {
    @EmbeddedId
    open var id: TentativeShiftDetailId? = tentativeShiftDetailId

    @Column(name = "is_num_of_people_insufficient", nullable = false)
    open var isNumOfPeopleInsufficient: Boolean? = numOfPeopleInsufficient

    @Column(name = "is_labor_insufficient", nullable = false)
    open var isLaborInsufficient: Boolean? = laborInsufficient
}