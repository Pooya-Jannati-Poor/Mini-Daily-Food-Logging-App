package ir.pooyadev.domain.common

import java.time.LocalDate
import java.time.ZoneId

fun getCurrentEpochDayInTehran(): Long {
    val tehranZone = ZoneId.of("Asia/Tehran")
    return LocalDate.now(tehranZone).toEpochDay()
}