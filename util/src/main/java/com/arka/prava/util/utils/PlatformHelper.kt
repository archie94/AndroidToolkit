package com.arka.prava.util.utils

import android.content.ContentValues
import android.content.Context
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import java.io.BufferedReader
import java.io.InputStreamReader

/**
 * https://github.com/cappee/droidinfo/blob/master/app/src/main/java/app/droidinfo/helper/AndroidHelper.java
 *
 * Created by Arka Prava Basu <arkaprava94@gmail.com> on 10/19/20.
 **/
object PlatformHelper {
    val androidVersion: String
        get() {
            var versionName = ""
            when (Build.VERSION.SDK_INT) {
                21 -> versionName = "Lollipop"
                22 -> versionName = "Lollipop"
                23 -> versionName = "Marshmallow"
                24 -> versionName = "Nougat"
                25 -> versionName = "Nougat"
                26 -> versionName = "Oreo"
                27 -> versionName = "Oreo"
                28 -> {
                    versionName = "Pie"
                    versionName = "Q"
                }
                29 -> versionName = "Q"
            }
            return versionName + " (" + Build.VERSION.RELEASE + ")"
        }
    val aPILevel: String
        get() = Build.VERSION.SDK_INT.toString()

    @get:RequiresApi(api = Build.VERSION_CODES.M)
    val securityPatch: String
        get() = Build.VERSION.SECURITY_PATCH
    val buildID: String
        get() = Build.DISPLAY

    fun getTreble(context: Context): Boolean {
        val output = getSystemProperty("ro.treble.enabled")
        return (output == "true")
    }

    fun getCustomRomName(context: Context): String {
        val output = getSystemProperty("org.pixelexperience.version")
        return if (output!!.toLowerCase().contains("PixelExperience".toLowerCase())) {
            "PixelExperience"
        } else {
            "Stock firmware"
        }
    }

    val kernelVersion: String
        get() = "Linux " + System.getProperty("os.version")
    val kernelArch: String?
        get() {
            val arch = System.getProperty("os.arch")
            return when (arch) {
                "armv7l" -> "ARMv7l"
                "aarch64" -> "Arch64"
                "32" -> "Unknown arch wtf u use xD"
                else -> arch
            }
        }
    val sELinuxStatus: String
        get() = if (isSELinuxEnforcing) {
            "Enforcing"
        } else {
            "Permissive"
        } // If getenforce is modified on this device, assume the device is not enforcing

    // If getenforce is not available to the device, assume the device is not enforcing
    private val isSELinuxEnforcing: Boolean
        private get() {
            val output = StringBuffer()
            val process: Process
            try {
                process = Runtime.getRuntime().exec("getenforce")
                process.waitFor()
                val reader = BufferedReader(InputStreamReader(process.inputStream))
                var line: String? = ""
                while (reader.readLine().also { line = it } != null) {
                    output.append(line)
                }
            } catch (e: Exception) {
                Log.e(ContentValues.TAG, "OS does not support getenforce")
                // If getenforce is not available to the device, assume the device is not enforcing
                e.printStackTrace()
                return false
            }
            val response = output.toString()
            return if ("Enforcing" == response) {
                true
            } else if ("Permissive" == response) {
                false
            } else {
                Log.e(
                    ContentValues.TAG,
                    "getenforce returned unexpected value, unable to determine selinux!"
                )
                // If getenforce is modified on this device, assume the device is not enforcing
                false
            }
        }

    fun getSystemProperty(key: String?): String? {
        var value: String? = null
        try {
            value = Class.forName("android.os.SystemProperties")
                .getMethod("get", String::class.java).invoke(null, key) as String
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return value
    }
}
