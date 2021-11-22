package com.rinats.rin.util

import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*

class DateUtil {
    companion object {
        fun getDate(year: Int, month: Int, day: Int): Date {

            val sYear = year.toString().padStart(4, '0')
            val sMonth = month.toString().padStart(2, '0')
            val sDay = day.toString().padStart(2, '0')
            val sDate = "$sYear$sMonth$sDay"

            try {
                val dateFormat = SimpleDateFormat("yyyyMMdd")
                dateFormat.isLenient = false
                return dateFormat.parse(sDate)
            } catch (e: ParseException) {
                throw ParseException("Can not parse $sDate to java.util.Date", 0)
            }
        }
    }
}