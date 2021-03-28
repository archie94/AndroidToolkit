package com.arka.prava.util.utils

import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale
import kotlin.math.ceil

/**
 * Created by Arka Prava Basu<arkaprava94@gmail.com> on 2019-05-26
 **/

fun getDateVerbose(epoch: Long, format: String): String {
    val date = Date(epoch)
    val sdf = SimpleDateFormat(format, Locale.ENGLISH)
    return sdf.format(date)
}

fun getHourAndMinOfDay(epoch: Long): Pair<Int, Int> {
    val calendar = Calendar.getInstance()
    calendar.timeInMillis = epoch * 1000L
    return Pair(calendar.get(Calendar.HOUR_OF_DAY), calendar.get(Calendar.MINUTE))
}

fun getEpoch(hour: Int, min: Int): Long {
    val calendar = Calendar.getInstance()
    calendar.set(Calendar.HOUR_OF_DAY, hour)
    calendar.set(Calendar.MINUTE, min)
    calendar.set(Calendar.SECOND, 0)
    calendar.timeZone
    val currentHourMinPair = getHourAndMinOfDay(System.currentTimeMillis() / 1000L)
    if (currentHourMinPair.first > hour ||
        currentHourMinPair.first == hour && currentHourMinPair.second > min
    ) {
        calendar.roll(Calendar.DATE, true)
    }
    return calendar.timeInMillis / 1000
}

fun getFormattedTime(epoch: Long, format: String): String {
    val dt = Date(epoch * 1000L)
    val sd = SimpleDateFormat(format, Locale.getDefault())
    return sd.format(dt)
}

fun isTomorrowEpoch(epoch: Long): Boolean {
    val c1 = Calendar.getInstance()
    c1.add(Calendar.DAY_OF_YEAR, 1) // tomorrow

    val c2 = Calendar.getInstance()
    c2.timeInMillis = epoch * 1000L

    return (
        c1.get(Calendar.YEAR) == c2.get(Calendar.YEAR) &&
            c1.get(Calendar.DAY_OF_YEAR) == c2.get(Calendar.DAY_OF_YEAR)
        )
}

fun isTodayEpoch(epoch: Long): Boolean {
    val c1 = Calendar.getInstance()
    val c2 = Calendar.getInstance()
    c2.timeInMillis = epoch * 1000L
    return (
        c1.get(Calendar.YEAR) == c2.get(Calendar.YEAR) &&
            c1.get(Calendar.DAY_OF_YEAR) == c2.get(Calendar.DAY_OF_YEAR)
        )
}

fun getFormattedMinutes(seconds: Int, minuteIdentifier: String): String {
    val minutes = ceil(seconds / 60f).toInt()
    return "$minutes $minuteIdentifier"
}
