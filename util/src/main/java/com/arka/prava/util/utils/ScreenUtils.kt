package com.arka.prava.util.utils

import android.app.Activity
import android.content.Context
import android.graphics.Point
import android.util.DisplayMetrics
import android.view.Display
import android.view.View
import android.view.WindowManager
import kotlin.math.pow
import kotlin.math.sqrt

fun Activity.getScreenHeightInPixels(): Int {
    return resources.displayMetrics.heightPixels
}

fun Context.getScreenHeightInPixels(): Int {
    return resources.displayMetrics.heightPixels
}

fun Activity.getScreenWidthInPixels(): Int {
    return resources.displayMetrics.widthPixels
}

fun Context.getScreenWidthInPixels(): Int {
    return resources.displayMetrics.widthPixels
}

fun Context.getNavigationBarHeightInPixels(): Int {
    val resources = resources
    val resourceId: Int = resources.getIdentifier("navigation_bar_height", "dimen", "android")
    return if (resourceId > 0) {
        resources.getDimensionPixelSize(resourceId)
    } else 0
}

fun Activity.getNavigationBarHeightInPixels(): Int {
    val resources = resources
    val resourceId: Int = resources.getIdentifier("navigation_bar_height", "dimen", "android")
    return if (resourceId > 0) {
        resources.getDimensionPixelSize(resourceId)
    } else 0
}

fun Activity.getStatusBarHeightInPixels(): Int {
    val resourceId = resources.getIdentifier("status_bar_height", "dimen", "android")
    return if (resourceId > 0) {
        resources.getDimensionPixelSize(resourceId)
    } else 0
}

fun View.goEdgeToEdge() {
    systemUiVisibility = View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION or
        View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN or
        View.SYSTEM_UI_FLAG_LAYOUT_STABLE
}

// https://developer.android.com/training/system-ui/immersive
fun View.goEdgeToEdgeWithNoBars() {
    systemUiVisibility = (
        View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
            // Set the content to appear under the system bars so that the
            // content doesn't resize when the system bars hide and show.
            or View.SYSTEM_UI_FLAG_LAYOUT_STABLE
            or View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
            or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
            // Hide the nav bar and status bar
            or View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
            or View.SYSTEM_UI_FLAG_FULLSCREEN
        )
}

fun View.applyStatusBarHeightMarginTop(activity: Activity, offset: Int = 0) {
    applyMarginTop(activity.getStatusBarHeightInPixels())
}

fun View.applyNavigationBarHeightMarginBottom(activity: Activity, offset: Int = 0) {
    applyMarginBottom(activity.getNavigationBarHeightInPixels())
}

fun Activity.getResolution(): Pair<Int, Int> {
    val display = windowManager.defaultDisplay
    val size = Point()
    display.getRealSize(size)
    val screenWidth = size.x
    val screenHeight = size.y
    return Pair(screenWidth, screenHeight)
}

fun Context.getDPI(): Int {
    val metrics = resources.displayMetrics
    return (metrics.density * 160f).toInt()
}

fun Activity.getScreenSize(): Double {
    var widthPixels = 0
    var heightPixels = 0
    val windowManager = windowManager
    val display = windowManager.defaultDisplay
    val displayMetrics = DisplayMetrics()
    display.getMetrics(displayMetrics)
    try {
        val realSize = Point()
        Display::class.java.getMethod("getRealSize", Point::class.java)
            .invoke(display, realSize)
        widthPixels = realSize.x
        heightPixels = realSize.y
    } catch (e: Exception) {
        e.printStackTrace()
    }
    val x = (widthPixels / displayMetrics.xdpi.toDouble()).pow(2.0)
    val y = (heightPixels / displayMetrics.ydpi.toDouble()).pow(2.0)
    return sqrt(x + y)
}

fun Activity.getRefreshValue(): Float {
    val display = (getSystemService(Context.WINDOW_SERVICE) as WindowManager).defaultDisplay
    return display.refreshRate
}

object DisplayHelper {

    /*public static String getCurrentBrightness() {
        int currentBrighness = 0;
        int maxBrighness = 0;
        try {
            RandomAccessFile randomAccessFileCurrent = new RandomAccessFile("/sys/class/leds/lcd-backlight/brightness", "r");
            boolean doneCurrent = false;
            while (!doneCurrent) {
                String line = randomAccessFileCurrent.readLine();
                if (null == line) {
                    doneCurrent = true;
                    break;
                }
                currentBrighness = Integer.parseInt(line);
            }
            RandomAccessFile randomAccessFileMax = new RandomAccessFile("/sys/class/leds/lcd-backlight/max_brightness", "r");
            boolean doneMax = false;
            while (!doneMax) {
                String line = randomAccessFileMax.readLine();
                if (null == line) {
                    doneMax = true;
                    break;
                }
                maxBrighness = Integer.parseInt(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return String.valueOf((currentBrighness*100)/maxBrighness) + "% (" + currentBrighness + " / " + maxBrighness +")";
    }*/
}
