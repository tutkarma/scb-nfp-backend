package dev.sorokin.hacksovcom.api

import java.sql.Time
import java.sql.Timestamp
import java.time.Instant
import java.time.OffsetTime
import java.time.ZoneOffset
import java.time.format.DateTimeFormatter
import java.util.*

/**
 * in seconds
 */
//typealias UnixTimestamp = Long

fun Timestamp.toSeconds(): Long  = this.toInstant().toEpochMilli() / 1000;

fun Long.toTimestamp(): Timestamp = Timestamp.from(Instant.ofEpochSecond(this))

//fun Date.toUnixTimestamp(): UnixTimestamp = time / 1000
//
//fun Instant.toUnixTimestamp(): UnixTimestamp = epochSecond
//
//fun UnixTimestamp.toInstant(): Instant = Instant.ofEpochSecond(this)
//
//inline fun now() = System.currentTimeMillis()
//
//fun Instant.toDate(): Date = Date.from(this)
//
///**
// * this = "12:00"
// * @return OffsetTime("12:00:01Z") with Pakistan time zone (+5)
// */
//fun String.toOffsetTime(): OffsetTime {
//    val withZone = DateTimeFormatter.ofPattern("HH:mm").withZone(ZoneOffset.ofHours(5))
//    return OffsetTime.parse(this, withZone)
//}
//
//fun OffsetTime.parse(): String {
//    return this.format(DateTimeFormatter.ofPattern("HH:mm:ssXXX")).substring(0, 5)
//}