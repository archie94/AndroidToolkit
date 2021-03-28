package com.arka.prava.util.utils

import android.app.ActivityManager
import android.content.Context
import android.os.Build
import java.io.BufferedReader
import java.io.File
import java.io.FileNotFoundException
import java.io.FileReader
import java.io.IOException
import java.io.RandomAccessFile
import java.util.Scanner
import javax.microedition.khronos.opengles.GL10
import kotlin.collections.HashMap

/**
 * https://github.com/cappee/droidinfo/blob/master/app/src/main/java/app/droidinfo/helper/SoCHelper.java
 *
 * Created by Arka Prava Basu <arkaprava94@gmail.com> on 10/20/20.
 **/
object SocHelper {
    fun getCPUInfoMap(): Map<String, String> {
        val map: MutableMap<String, String> = HashMap()
        try {
            val scanner = Scanner(File("/proc/cpuinfo"))
            while (scanner.hasNextLine()) {
                val vals = scanner.nextLine().split(": ".toRegex()).toTypedArray()
                if (vals.size > 1) map[vals[0].trim { it <= ' ' }] = vals[1].trim { it <= ' ' }
            }
        } catch (e: FileNotFoundException) {
            e.printStackTrace()
        }
        return map
    }

    fun getIfIs64bit(): Boolean {
        var isArm64Method1 = false
        try {
            val localBufferedReader = BufferedReader(FileReader("/proc/cpuinfo"))
            if (localBufferedReader.readLine().contains("aarch64")) {
                isArm64Method1 = true
            }
            localBufferedReader.close()
        } catch (ignored: IOException) {
        }
        val isArm64Method2: Boolean = Build.SUPPORTED_64_BIT_ABIS.isNotEmpty()
        return (isArm64Method1 || isArm64Method2)
    }

    fun getCPUModel(): String? {
        var cpu = getCPUInfoMap()["Hardware"]
        if (cpu == null) {
            cpu = getCPUInfoMap()["model name"]
        }
        return cpu
    }

    fun getCPUCores(): Int {
        return Runtime.getRuntime().availableProcessors()
    }

    /**
     * Min and max freq of the core. Value in MHz
     */
    fun getCPUFreq(core: Int = 0): Pair<Int, Int> {
        return Pair(getMinCPUFreq(core), getMaxCPUFreq(core))
    }

    fun getCurrentCPUFreq(core: Int): String {
        var currentFreq = -1
        try {
            val randomAccessFile =
                RandomAccessFile("/sys/devices/system/cpu/cpu$core/cpufreq/scaling_cur_freq", "r")
            var done = false
            while (!done) {
                val line = randomAccessFile.readLine()
                if (null == line) {
                    done = true
                    break
                }
                val timeInState = line.toInt()
                if (timeInState > 0) {
                    val freq = timeInState / 1000
                    if (freq > currentFreq) {
                        currentFreq = freq
                    }
                }
            }
        } catch (e: IOException) {
            e.printStackTrace()
        }
        return currentFreq.toString()
    }

    private fun getMinCPUFreq(core: Int): Int {
        var minFreq = -1
        try {
            val randomAccessFile =
                RandomAccessFile("/sys/devices/system/cpu/cpu$core/cpufreq/cpuinfo_min_freq", "r")
            var done = false
            while (!done) {
                val line = randomAccessFile.readLine()
                if (null == line) {
                    done = true
                    break
                }
                val timeInState = line.toInt()
                if (timeInState > 0) {
                    val freq = timeInState / 1000
                    if (freq > minFreq) {
                        minFreq = freq
                    }
                }
            }
        } catch (e: IOException) {
            e.printStackTrace()
        }
        return minFreq
    }

    private fun getMaxCPUFreq(core: Int): Int {
        var maxFreq = -1
        try {
            val randomAccessFile =
                RandomAccessFile("/sys/devices/system/cpu/cpu$core/cpufreq/cpuinfo_max_freq", "r")
            var done = false
            while (!done) {
                val line = randomAccessFile.readLine()
                if (null == line) {
                    done = true
                    break
                }
                val timeInState = line.toInt()
                if (timeInState > 0) {
                    val freq = timeInState / 1000
                    if (freq > maxFreq) {
                        maxFreq = freq
                    }
                }
            }
        } catch (e: IOException) {
            e.printStackTrace()
        }
        return maxFreq
    }

    fun getCPUGovernor(core: Int): String {
        var governor = ""
        val file = "/sys/devices/system/cpu/cpu$core/cpufreq/scaling_governor"
        if (File(file).exists()) {
            try {
                val bufferedReader = BufferedReader(FileReader(File(file)))
                governor = bufferedReader.readLine()
                bufferedReader.close()
            } catch (e: IOException) {
                e.printStackTrace()
            }
        }
        return governor
    }

    fun getBogoMIPS(): String {
        var bogomips = getCPUInfoMap()["bogomips"].toString()
        if (bogomips == "null") {
            bogomips = getCPUInfoMap()["BogoMIPS"].toString()
        }
        return bogomips
    }

    fun getGPUVendor(gl10: GL10): String {
        return gl10.glGetString(GL10.GL_VENDOR)
    }

    fun getGPURenderer(gl10: GL10): String {
        return gl10.glGetString(GL10.GL_RENDERER)
    }

    fun getOpenGLVersion(context: Context): String {
        val activityManager = context.getSystemService(Context.ACTIVITY_SERVICE) as ActivityManager
        val configurationInfo = activityManager.deviceConfigurationInfo
        return configurationInfo.glEsVersion
    }
}
