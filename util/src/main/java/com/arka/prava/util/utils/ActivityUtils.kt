package com.arka.prava.util.utils

import android.app.Activity
import android.app.ActivityManager
import android.content.ActivityNotFoundException
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AlertDialog

/**
 * Created by Arka Prava Basu<arkaprava94@gmail.com.com> on 2019-12-01
 **/
fun Activity.showAlertDialog(s: String) {
    AlertDialog.Builder(this)
        .setMessage(s)
        .create()
        .show()
}

fun Activity.showToast(msg: String, length: Int = Toast.LENGTH_SHORT) {
    Toast.makeText(this, msg, length).show()
}

fun EditText.showKeyboard() {
    requestFocus()
    val imm = context.getSystemService(Context.INPUT_METHOD_SERVICE) as
        InputMethodManager
    imm.showSoftInput(this, InputMethodManager.SHOW_IMPLICIT)
}

fun EditText.hideKeyboard() {
    val imm = context.getSystemService(Context.INPUT_METHOD_SERVICE) as
        InputMethodManager
    imm.hideSoftInputFromWindow(this.windowToken, 0)
}

fun Activity.isEs2Supported(): Boolean {
    val activityManager = getSystemService(Context.ACTIVITY_SERVICE) as ActivityManager
    val configurationInfo = activityManager.deviceConfigurationInfo
    return configurationInfo.reqGlEsVersion >= 0x20000
}

/**
 * Check if OpenGL ES 3.1 [android.opengl.GLES31] is supported
 */
fun Activity.isEs31Supported(): Boolean {
    val activityManager = getSystemService(Context.ACTIVITY_SERVICE) as ActivityManager
    val configurationInfo = activityManager.deviceConfigurationInfo
    return configurationInfo.reqGlEsVersion >= 0x00030001
}

fun Activity.shareMessage(
    requestCode: Int,
    shareSubject: String,
    shareBody: String,
    chooserTitle: String?,
    streamUri: Uri?,
    appPackageName: String? = null
) {
    val sharingIntent = Intent(Intent.ACTION_SEND).apply {
        type = "text/plain"
        putExtra(Intent.EXTRA_SUBJECT, shareSubject)
        putExtra(Intent.EXTRA_TEXT, shareBody)
        streamUri?.let {
            putExtra(Intent.EXTRA_STREAM, it)
            flags = Intent.FLAG_GRANT_READ_URI_PERMISSION
        }
        if (appPackageName != null)
            setPackage(appPackageName)
    }
    startActivityForResult(Intent.createChooser(sharingIntent, chooserTitle), requestCode)
}

fun Activity.shareImage(
    requestCode: Int,
    shareSubject: String,
    shareBody: String,
    chooserTitle: String?,
    imageUri: Uri,
    appPackageName: String? = null
) {
    val sharingIntent = Intent(Intent.ACTION_SEND).apply {
        type = "image/*"
        putExtra(Intent.EXTRA_SUBJECT, shareSubject)
        putExtra(Intent.EXTRA_TEXT, shareBody)
        putExtra(Intent.EXTRA_STREAM, imageUri)
        flags = Intent.FLAG_GRANT_READ_URI_PERMISSION
        if (appPackageName != null)
            setPackage(appPackageName)
    }
    startActivityForResult(Intent.createChooser(sharingIntent, chooserTitle), requestCode)
}

@AppExperimental
@Throws(ActivityNotFoundException::class)
fun Activity.shareSms(phoneNo: String, sms: String) {
    val smsIntent = Intent(Intent.ACTION_VIEW, Uri.fromParts("sms", phoneNo, null))
    smsIntent.putExtra("sms_body", sms)
    startActivity(smsIntent)
}

@AppExperimental
@Throws(ActivityNotFoundException::class)
fun Activity.shareEmail(body: String?, subject: String?, addressList: Array<String>) {
    val emailIntent = Intent(
        Intent.ACTION_SENDTO
    ).apply {
        data = Uri.parse("mailto:") // only email apps should handle this
        putExtra(Intent.EXTRA_SUBJECT, subject)
        putExtra(Intent.EXTRA_TEXT, body)
        putExtra(Intent.EXTRA_EMAIL, addressList); // String[] addresses
    }
    startActivity(Intent.createChooser(emailIntent, "Send email..."))
}
