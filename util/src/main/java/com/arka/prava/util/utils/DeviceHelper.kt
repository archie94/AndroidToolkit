package com.arka.prava.util.utils

import android.app.Activity
import android.app.ActivityManager
import android.content.Context
import android.nfc.NfcManager
import android.os.Build
import android.os.Environment
import android.os.StatFs
import androidx.core.content.ContextCompat
import java.io.BufferedReader
import java.io.File
import java.io.IOException
import java.io.InputStreamReader
import java.io.RandomAccessFile
import java.text.DecimalFormat
import java.util.regex.Pattern

/**
 * https://github.com/cappee/droidinfo/blob/master/app/src/main/java/app/droidinfo/helper/DeviceHelper.java
 *
 * Created by Arka Prava Basu <arkaprava94@gmail.com> on 10/19/20.
 **/
object DeviceHelper {
    val model: String
        get() = Build.MODEL
    val codename: String
        get() = Build.DEVICE
    val manufacturer: String
        get() = Build.MANUFACTURER

    fun Context.getRAM(): String {
        val randomAccessFile: RandomAccessFile
        val load: String
        val decimalFormat = DecimalFormat("#.##")
        val totRAM: Double
        var lastValue = ""
        try {
            randomAccessFile = RandomAccessFile("/proc/meminfo", "r")
            load = randomAccessFile.readLine()
            val pattern = Pattern.compile("(\\d+)")
            val matcher = pattern.matcher(load)
            var value = ""
            while (matcher.find()) {
                value = matcher.group(1)
            }
            randomAccessFile.close()
            totRAM = value.toDouble()
            val mb = totRAM / 1024.0
            val gb = totRAM / 1048576.0
            val tb = totRAM / 1073741824.0
            lastValue = if (tb > 1) {
                decimalFormat.format(tb) + " TB"
            } else if (gb > 1) {
                decimalFormat.format(gb) + " GB"
            } else if (mb > 1) {
                decimalFormat.format(mb) + " MB"
            } else {
                decimalFormat.format(totRAM) + " KB"
            }
        } catch (ex: IOException) {
            ex.printStackTrace()
        }
        val mi = ActivityManager.MemoryInfo()
        val activityManager = getSystemService(Context.ACTIVITY_SERVICE) as ActivityManager
        activityManager.getMemoryInfo(mi)
        val ramAvailable = mi.availMem / 0x100000L // 0x100000L is a mebibyte - StopCopyAnything
        var ramAvailableString = ""
        if (ramAvailable < 1024) {
            ramAvailableString = "$ramAvailable MB"
        } else if (ramAvailable > 1024) {
            ramAvailableString = (ramAvailable / 1024).toString() + " GB"
        }
        return "$ramAvailableString / $lastValue"
    }

    fun getDeviceRAM(): Long {
        val randomAccessFile: RandomAccessFile
        val load: String
        val decimalFormat = DecimalFormat("#.##")
        var totRAM: Long = 0
        var lastValue = ""
        try {
            randomAccessFile = RandomAccessFile("/proc/meminfo", "r")
            load = randomAccessFile.readLine()
            val pattern = Pattern.compile("(\\d+)")
            val matcher = pattern.matcher(load)
            var value = ""
            while (matcher.find()) {
                value = matcher.group(1)
            }
            randomAccessFile.close()
            totRAM = value.toLong()
        } catch (ex: IOException) {
            ex.printStackTrace()
        }
        return totRAM
    }

    private fun getExternalStorageDirectories(context: Activity): String {
        val results: MutableList<String> = ArrayList()

        // Method 1 for KitKat & above
        val externalDirs = context.getExternalFilesDirs(null)
        for (file in externalDirs) {
            val path = file.path.split("/Android".toRegex()).toTypedArray()[0]
            var addPath: Boolean
            addPath = Environment.isExternalStorageRemovable(file)
            if (addPath) {
                results.add(path)
            }
        }
        if (results.isEmpty()) { // Method 2 for all versions
            // better variation of: http://stackoverflow.com/a/40123073/5002496
            var output = ""
            try {
                val process = ProcessBuilder().command("mount | grep /dev/block/vold")
                    .redirectErrorStream(true).start()
                process.waitFor()
                val `is` = process.inputStream
                val buffer = ByteArray(1024)
                while (`is`.read(buffer) != -1) {
                    output = output + String(buffer)
                }
                `is`.close()
            } catch (e: Exception) {
                e.printStackTrace()
            }
            if (!output.trim { it <= ' ' }.isEmpty()) {
                val devicePoints = output.split("\n".toRegex()).toTypedArray()
                for (voldPoint in devicePoints) {
                    results.add(voldPoint.split(" ".toRegex()).toTypedArray()[2])
                }
            }
        }

        // Below few lines is to remove paths which may not be external memory card, like OTG (feel free to comment them out)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            var i = 0
            while (i < results.size) {
                if (!results[i].toLowerCase().matches(Regex(".*[0-9a-f]{4}[-][0-9a-f]{4}"))) {
                    // Log.d(LOG_TAG, results.get(i) + " might not be extSDcard");
                    results.removeAt(i--)
                }
                i++
            }
        } else {
            var i = 0
            while (i < results.size) {
                if (!results[i].toLowerCase().contains("ext") && !results[i].toLowerCase()
                    .contains("sdcard")
                ) {
                    // Log.d(LOG_TAG, results.get(i)+" might not be extSDcard");
                    results.removeAt(i--)
                }
                i++
            }
        }
        var storageDirectories = ""
        for (i in results.indices) storageDirectories = storageDirectories + results[i]
        return storageDirectories
    }

    val internalStorage: String
        get() {
            val statFs = StatFs(Environment.getDataDirectory().path)
            val lastValue: String
            val decimalFormat = DecimalFormat("#.##")
            val blockSize = statFs.blockSize.toLong()
            val totalSize = statFs.blockCount * blockSize
            val kb = totalSize / 1024.0
            val mb = totalSize / 1048576.0
            val gb = totalSize / 1073741824.0
            lastValue = if (gb > 1) {
                decimalFormat.format(gb) + " GB"
            } else if (mb > 1) {
                decimalFormat.format(mb) + " MB"
            } else if (kb > 1) {
                decimalFormat.format(kb) + " KB"
            } else {
                decimalFormat.format(totalSize) + " bytes"
            }
            return lastValue
        }

    fun getExternalStorage(context: Activity): String {
        return if (ContextCompat.getExternalFilesDirs(context, null).size >= 2) {
            val lastValue: String
            val statFs = StatFs(getExternalStorageDirectories(context))
            val blockSize = statFs.blockSizeLong
            val totalBlocks = statFs.blockCountLong
            val totalSize = blockSize * totalBlocks
            val decimalFormat = DecimalFormat("#.##")
            val kb = totalSize / 1024.0
            val mb = totalSize / 1048576.0
            val gb = totalSize / 1073741824.0
            lastValue = if (gb > 1) {
                decimalFormat.format(gb) + " GB"
            } else if (mb > 1) {
                decimalFormat.format(mb) + " MB"
            } else if (kb > 1) {
                decimalFormat.format(kb) + " KB"
            } else {
                decimalFormat.format(totalSize) + " bytes"
            }
            lastValue
        } else {
            "Not mounted"
        }
    }

    fun Context.getIfNFCIsPresent(): Nfc {
        val nfcManager = getSystemService(Context.NFC_SERVICE) as NfcManager
        val nfcAdapter = nfcManager.defaultAdapter
        return if (nfcAdapter != null && nfcAdapter.isEnabled) {
            Nfc.ENABLED
        } else if (nfcAdapter != null && !nfcAdapter.isEnabled) {
            Nfc.DISABLED
        } else {
            Nfc.NA
        }
    }

    fun getRootAccess(): Boolean {
        return checkRootMethod1() || checkRootMethod2() || checkRootMethod3()
    }

    private fun checkRootMethod1(): Boolean {
        val buildTags = Build.TAGS
        return buildTags != null && buildTags.contains("test-keys")
    }

    private fun checkRootMethod2(): Boolean {
        val paths = arrayOf(
            "/system/app/Superuser.apk",
            "/sbin/su",
            "/system/bin/su",
            "/system/xbin/su",
            "/data/local/xbin/su",
            "/data/local/bin/su",
            "/system/sd/xbin/su",
            "/system/bin/failsafe/su",
            "/data/local/su",
            "/su/bin/su"
        )
        for (path in paths) { // for each bitch hahahha joke man xD - StopCopyAnything
            if (File(path).exists()) return true
        }
        return false
    }

    private fun checkRootMethod3(): Boolean {
        var process: Process? = null
        return try {
            process = Runtime.getRuntime().exec(arrayOf("/system/xbin/which", "su"))
            val bufferedReader = BufferedReader(InputStreamReader(process.inputStream))
            bufferedReader.readLine() != null
        } catch (t: Throwable) {
            false
        } finally {
            process?.destroy()
        }
    }
}

enum class Nfc {
    NA, DISABLED, ENABLED
}
