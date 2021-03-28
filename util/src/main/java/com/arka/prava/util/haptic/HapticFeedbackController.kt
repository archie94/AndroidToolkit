package com.arka.prava.util.haptic

import android.Manifest
import android.app.Service
import android.content.Context
import android.content.pm.PackageManager
import android.database.ContentObserver
import android.os.Build
import android.os.SystemClock
import android.os.VibrationEffect
import android.os.Vibrator
import android.provider.Settings
import androidx.annotation.RequiresApi
import androidx.annotation.RequiresPermission
import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.LifecycleOwner
import timber.log.Timber

/**
 * A simple utility class to handle haptic feedback.
 *
 * Inspired from Material DateTimePicker Util class
 */
class HapticFeedbackController(private val mContext: Context, lifecycleOwner: LifecycleOwner) :
    DefaultLifecycleObserver {
    private val contentObserver: ContentObserver
    private var vibrator: Vibrator? = null
    private var isGloballyEnabled = false
    private var lastVibrate: Long = 0

    override fun onStart(owner: LifecycleOwner) {
        super.onStart(owner)
        start()
    }

    override fun onStop(owner: LifecycleOwner) {
        stop()
        super.onStop(owner)
    }

    /**
	 * Call to setup the controller.
	 */
    private fun start() {
        if (hasVibratePermission(mContext)) {
            vibrator =
                (mContext.getSystemService(Service.VIBRATOR_SERVICE) as Vibrator).takeIf { it.hasVibrator() }
        }

        // Setup a listener for changes in haptic feedback settings
        isGloballyEnabled = checkGlobalSetting(mContext)
        val uri =
            Settings.System.getUriFor(Settings.System.HAPTIC_FEEDBACK_ENABLED)
        mContext.contentResolver.registerContentObserver(uri, false, contentObserver)
    }

    /**
	 * Method to verify that vibrate permission has been granted.
	 *
	 * Allows users of the library to disabled vibrate support if desired.
	 * @return true if Vibrate permission has been granted
	 */
    private fun hasVibratePermission(context: Context): Boolean {
        val pm = context.packageManager
        val hasPerm =
            pm.checkPermission(Manifest.permission.VIBRATE, context.packageName)
        return hasPerm == PackageManager.PERMISSION_GRANTED
    }

    /**
	 * Call this when you don't need the controller anymore.
	 */
    private fun stop() {
        vibrator = null
        mContext.contentResolver.unregisterContentObserver(contentObserver)
    }

    /**
	 * Try to vibrate. To prevent this becoming a single continuous vibration, nothing will
	 * happen if we have vibrated very recently.
	 */
    @RequiresPermission(Manifest.permission.VIBRATE)
    fun tryVibrate() {
        val vibrator = this.vibrator ?: kotlin.run {
            Timber.d("Vibrator is null")
            return
        }
        if (isGloballyEnabled) {
            val now = SystemClock.uptimeMillis()
            // Vibrate with a time threshold
            if (now - lastVibrate >= VIBRATE_DELAY_MS) {
                vibrator.vibrate(VIBRATE_LENGTH_MS.toLong())
                lastVibrate = now
            }
        }
    }

    /**
     * Try to vibrate. To prevent this becoming a single continuous vibration, nothing will
     * happen if we have vibrated very recently.
     */
    @RequiresApi(Build.VERSION_CODES.O)
    @RequiresPermission(Manifest.permission.VIBRATE)
    fun tryVibrate(effect: VibrationEffect) {
        val vibrator = this.vibrator ?: kotlin.run {
            Timber.d("Vibrator is null")
            return
        }
        if (isGloballyEnabled) {
            val now = SystemClock.uptimeMillis()
            // Vibrate with a time threshold
            if (now - lastVibrate >= VIBRATE_DELAY_MS) {
                vibrator.vibrate(effect)
                lastVibrate = now
            }
        }
    }

    companion object {
        private const val VIBRATE_DELAY_MS = 125
        private const val VIBRATE_LENGTH_MS = 50
        private fun checkGlobalSetting(context: Context): Boolean {
            return Settings.System.getInt(
                context.contentResolver,
                Settings.System.HAPTIC_FEEDBACK_ENABLED,
                0
            ) == 1
        }
    }

    init {
        contentObserver = object : ContentObserver(null) {
            override fun onChange(selfChange: Boolean) {
                isGloballyEnabled = checkGlobalSetting(mContext)
            }
        }
        lifecycleOwner.lifecycle.addObserver(this)
    }
}
