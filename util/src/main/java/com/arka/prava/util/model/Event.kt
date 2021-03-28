package com.arka.prava.util.model

/**
 * Created by Arka Prava Basu <arkaprava94@gmail.com> on 10/9/19.
 */
open class Event<T>(data: T) {
    private var dataInternal: T? = data
    fun consume(): T? {
        dataInternal ?: return null
        val copy = dataInternal
        dataInternal = null
        return copy
    }
}
