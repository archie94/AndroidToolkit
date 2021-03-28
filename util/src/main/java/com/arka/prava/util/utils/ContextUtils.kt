package com.arka.prava.util.utils

import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.drawable.Drawable
import android.provider.Settings

/**
 * Context can be application context
 */
@Deprecated("Usage of this key is almost never needed")
fun Context.getAndroidId(): String =
    Settings.Secure.getString(contentResolver, Settings.Secure.ANDROID_ID)

fun Context.getShareAppList(dataType: String = "text/plain"): List<AppInfo> {
    val sharingIntent = Intent(Intent.ACTION_SEND).apply {
        type = dataType
    }
    val list = mutableListOf<AppInfo>()
    val resolveInfoList =
        packageManager.queryIntentActivities(sharingIntent, PackageManager.GET_META_DATA)
    resolveInfoList.mapTo(list) { resolveInfo ->
        AppInfo(
            resolveInfo.activityInfo.packageName,
            resolveInfo.activityInfo.loadIcon(packageManager),
            resolveInfo.loadLabel(packageManager)?.toString() ?: ""
        )
    }
    return list.unique { item1, item2 ->
        item1.packageName == item2.packageName
    }
}

data class AppInfo(
    val packageName: String,
    val appIcon: Drawable,
    val componentName: String
)
