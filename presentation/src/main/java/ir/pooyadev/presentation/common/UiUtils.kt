package ir.pooyadev.presentation.common

import saman.zamani.persiandate.PersianDate
import java.text.SimpleDateFormat
import java.time.ZoneId
import java.time.ZonedDateTime
import java.util.Date
import java.util.Locale
import java.util.TimeZone

private const val TEHRAN_ZONE_ID = "Asia/Tehran"

fun Long.toPersianTimeString(): String {
    val date = Date(this)
    val formatter = SimpleDateFormat("HH:mm", Locale.getDefault())
    formatter.timeZone = TimeZone.getTimeZone(TEHRAN_ZONE_ID)
    return formatter.format(date)
}

fun getTodayShamsiDate(): String {
    val tehranZone = ZoneId.of(TEHRAN_ZONE_ID)
    val nowInTehran = ZonedDateTime.now(tehranZone)
    val date = Date.from(nowInTehran.toInstant())
    val persianDate = PersianDate(date)

    val day = persianDate.shDay
    val month = persianDate.shMonth
    val year = persianDate.shYear

    return "$year/$month/$day"
}