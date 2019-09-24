package com.prava.arka.common.util

import java.text.SimpleDateFormat
import java.util.*

/**
 * Created by Arka Prava Basu<arkaprava94@gmail.com> on 2019-05-26
 **/

fun getDateVerbose(epoch: Long, format: String): String {
    val date = Date(epoch)
    val sdf = SimpleDateFormat(format, Locale.ENGLISH)
    return sdf.format(date)
}
