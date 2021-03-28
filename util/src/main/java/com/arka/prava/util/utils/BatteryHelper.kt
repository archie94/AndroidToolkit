package com.arka.prava.util.utils

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.BatteryManager

/**
 * https://github.com/cappee/droidinfo/blob/master/app/src/main/java/app/droidinfo/helper/BatteryHelper.java
 *
 * Created by Arka Prava Basu <arkaprava94@gmail.com> on 10/19/20.
 **/
object BatteryHelper {
    private fun Intent.isBatteryPresent(): Boolean {
        return getBooleanExtra(BatteryManager.EXTRA_PRESENT, false)
    }

    fun Intent.getBatteryHealth(activity: Activity): BatteryHealth {
        return if (isBatteryPresent()) {
            when (getIntExtra(BatteryManager.EXTRA_HEALTH, 0)) {
                BatteryManager.BATTERY_HEALTH_COLD -> BatteryHealth.COLD
                BatteryManager.BATTERY_HEALTH_DEAD -> BatteryHealth.DEAD
                BatteryManager.BATTERY_HEALTH_GOOD -> BatteryHealth.GOOD
                BatteryManager.BATTERY_HEALTH_OVER_VOLTAGE -> BatteryHealth.OVER_VOLTAGE
                BatteryManager.BATTERY_HEALTH_OVERHEAT -> BatteryHealth.OVERHEAT
                BatteryManager.BATTERY_HEALTH_UNSPECIFIED_FAILURE -> BatteryHealth.FAILURE
                BatteryManager.BATTERY_HEALTH_UNKNOWN -> BatteryHealth.UNKNOWN
                else -> BatteryHealth.UNKNOWN
            }
        } else {
            BatteryHealth.NA
        }
    }

    fun Intent.getBatteryPercentage(): Float? {
        val level = getIntExtra(BatteryManager.EXTRA_LEVEL, -1)
        val scale = getIntExtra(BatteryManager.EXTRA_SCALE, -1)
        return if (level != -1 && scale != -1) {
            (level / scale.toFloat() * 100f)
        } else {
            null
        }
    }

    fun Intent.getPluggedSource(activity: Activity): ChargingSource {
        return when (getIntExtra(BatteryManager.EXTRA_PLUGGED, 0)) {
            BatteryManager.BATTERY_PLUGGED_WIRELESS -> ChargingSource.WIRELESS
            BatteryManager.BATTERY_PLUGGED_USB -> ChargingSource.USB
            BatteryManager.BATTERY_PLUGGED_AC -> ChargingSource.AC
            else -> ChargingSource.DISCONNECTED
        }
    }

    /**
     * Get battery status
     */
    fun Intent.getBatteryStatus(): BatteryStatus {
        return when (getIntExtra(BatteryManager.EXTRA_STATUS, -1)) {
            BatteryManager.BATTERY_STATUS_CHARGING -> BatteryStatus.CHARGING
            BatteryManager.BATTERY_STATUS_DISCHARGING -> BatteryStatus.DISCHARGING
            BatteryManager.BATTERY_STATUS_FULL -> BatteryStatus.FULL
            BatteryManager.BATTERY_STATUS_UNKNOWN -> BatteryStatus.UNKNOWN
            BatteryManager.BATTERY_STATUS_NOT_CHARGING -> BatteryStatus.NOT_CHARGING
            else -> BatteryStatus.UNKNOWN
        }
    }

    /**
     * Get battery technology
     */
    fun Intent.getBatteryTechnology(intent: Intent): String? {
        return if (intent.extras != null) {
            val technology = intent.extras!!.getString(BatteryManager.EXTRA_TECHNOLOGY)
            if ("" != technology) {
                technology
            } else {
                null
            }
        } else {
            null
        }
    }

    /**
     * Get battery temp in °C
     *
     * `null` if unknown
     */
    fun Intent.getBatteryTemperature(): Float? {
        val temperature = getIntExtra(BatteryManager.EXTRA_TEMPERATURE, 0)
        return if (temperature > 0) {
            (temperature.toFloat() / 10f)
        } else {
            null
        }
    }

    /**
     * Gets the voltage in mV
     *
     * `null` if unknown
     */
    fun getVoltage(intent: Intent, activity: Activity): Int? {
        val voltage = intent.getIntExtra(BatteryManager.EXTRA_VOLTAGE, 0)
        return if (voltage > 0) {
            voltage
        } else {
            null
        }
    }

    /**
     * Get battery capacity in mAh
     *
     * `null` if unknown
     */
    fun Context.getBatteryCapacity(): Double? {
        var powerProfile_: Any? = null
        val POWER_PROFILE_CLASS = "com.android.internal.os.PowerProfile"
        try {
            powerProfile_ = Class.forName(POWER_PROFILE_CLASS).getConstructor(Context::class.java)
                .newInstance(this)
        } catch (e: Exception) {
            e.printStackTrace()
        }
        var batteryCapacity = 0.0
        try {
            batteryCapacity = Class.forName(POWER_PROFILE_CLASS).getMethod(
                "getAveragePower",
                String::class.java
            ).invoke(powerProfile_, "battery.capacity") as Double
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return if (batteryCapacity == 1000.0) {
            null
        } else {
            batteryCapacity
        }
    }
}

enum class BatteryHealth {
    NA, UNKNOWN, FAILURE, OVERHEAT, OVER_VOLTAGE, COLD, DEAD, GOOD
}

enum class BatteryStatus {
    UNKNOWN, NOT_CHARGING, DISCHARGING, CHARGING, FULL
}

enum class ChargingSource {
    DISCONNECTED, AC, USB, WIRELESS
}
