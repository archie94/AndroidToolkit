package com.arka.prava.util.interfaces

interface Provider {
    fun <T> get(clazz: Class<T>): T?
}
