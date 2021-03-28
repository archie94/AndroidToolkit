package com.arka.prava.util.utils

/**
 * Created by Arka Prava Basu <arkaprava94@gmail.com> on 9/10/20.
 **/
/**
 * Same as [assert] except that this wont require JVM runtime flags
 */
inline fun checkCondition(value: Boolean, lazyMessage: () -> Any = { "Assertion failed" }) {
    if (!value) {
        val message = lazyMessage()
        throw AssertionError(message)
    }
}
