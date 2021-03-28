package com.arka.prava.util.helper

import android.os.Handler
import android.view.View

/**
 * Created by Arka Prava Basu <arkaprava94@gmail.com> on 10/5/20.
 **/
class DelayedClickListener(
    private var delayInMillis: Long = MINIMUM_INTERVAL_MSEC,
    minimumIntervalMsec: Long = 2 * LONG_MINIMUM_INTERVAL_MSEC,
    private val clickInterface: ClickInterface?
) :
    DebouncedOnClickListener(minimumIntervalMsec) {

    override fun onDebouncedClick(v: View?) {
        val handler = v?.handler ?: Handler()
        handler.postDelayed({ clickInterface?.onClick(v) }, delayInMillis)
    }

    @FunctionalInterface
    interface ClickInterface {
        fun onClick(v: View?)
    }
}
