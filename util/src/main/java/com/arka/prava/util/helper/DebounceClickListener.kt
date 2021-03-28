package com.arka.prava.util.helper

import android.os.SystemClock
import android.view.View
import java.util.WeakHashMap
import kotlin.math.abs

/**
 * Created by Arka Prava Basu <arkaprava94@gmail.com> on 10/5/20.
 **/
abstract class DebouncedOnClickListener @JvmOverloads constructor(minimumIntervalMsec: Long = LONG_MINIMUM_INTERVAL_MSEC) :
    View.OnClickListener {
    private val minimumInterval: Long
    private val lastClickMap: MutableMap<View, Long>

    /**
     * Implement this in your subclass instead of onClick
     *
     * @param v The view that was clicked
     */
    abstract fun onDebouncedClick(v: View?)

    final override fun onClick(clickedView: View) {
        val previousClickTimestamp = lastClickMap[clickedView]
        val currentTimestamp: Long = SystemClock.uptimeMillis()
        lastClickMap[clickedView] = currentTimestamp
        if (previousClickTimestamp == null || abs(currentTimestamp - previousClickTimestamp.toLong()) > minimumInterval) {
            onDebouncedClick(clickedView)
        }
    }

    companion object {
        const val MINIMUM_INTERVAL_MSEC: Long = 100
        const val LONG_MINIMUM_INTERVAL_MSEC: Long = 300
    }

    /**
     * The constructor with custom mimimum time between clicks
     *
     * @param minimumIntervalMsec The minimum allowed time between clicks - any click sooner than this after a previous click will be rejected
     */
    /**
     * The constructor with LONG_MINIMUM_INTERVAL_MSEC time between clicks
     */
    init {
        minimumInterval =
            if (minimumIntervalMsec > 0) minimumIntervalMsec else MINIMUM_INTERVAL_MSEC
        lastClickMap = WeakHashMap<View, Long>()
    }
}
