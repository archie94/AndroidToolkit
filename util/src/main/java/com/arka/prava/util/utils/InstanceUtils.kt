package com.arka.prava.util.utils

/**
 * Created by Arka Prava Basu<arkaprava94@gmail.com> on 2019-10-30
 **/

fun Any.getTag() = this::class.java.simpleName
fun Any.getTag2() = this::class.java.canonicalName
