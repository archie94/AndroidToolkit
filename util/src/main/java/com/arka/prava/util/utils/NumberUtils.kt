package com.arka.prava.util.utils

import java.util.Locale

/**
 * Created by Arka Prava Basu <arkaprava94@gmail.com> on 2/10/21.
 **/
const val g = 9.81998 // acceleration due to gravity in m/s^2

fun Float.roundTo(n: Int): Float {
    return "%.${n}f".format(Locale.ENGLISH, this).toFloat()
}

fun Double.roundTo(n: Int): Double {
    return "%.${n}f".format(Locale.ENGLISH, this).toDouble()
}

fun percent(x: Int, total: Int): Double = (x.toDouble() / total) * 100.0
