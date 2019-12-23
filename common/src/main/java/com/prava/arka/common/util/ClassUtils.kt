package com.prava.arka.common.util

import kotlin.reflect.KClass

/**
 * Created by Arka Prava Basu <arkaprava94@gmail.com> on 19/10/19.
 */
fun KClass<*>.getTag(): String = this.java.simpleName
fun KClass<*>.getTag2(): String? = this.java.canonicalName

fun Class<Any>.getTag() = this.simpleName
fun Class<Any>.getTag2(): String? = this.canonicalName
