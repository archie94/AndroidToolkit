package com.arka.prava.util.utils

import android.os.Looper

fun assertMainThread() {
    if (Looper.myLooper() != Looper.getMainLooper()) {
        throw IllegalStateException("Not on main thread!")
    }
}

fun assertWorkerThread() {
    if (Looper.myLooper() == Looper.getMainLooper()) {
        throw IllegalStateException("Not on worker thread!")
    }
}
