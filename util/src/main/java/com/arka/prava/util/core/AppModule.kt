package com.arka.prava.util.core

interface AppModule {
    /**
     * Initialize a module. Returns [Boolean.TRUE] iff successful.
     */
    fun init(): Boolean
}
