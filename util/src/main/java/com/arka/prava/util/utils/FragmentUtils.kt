package com.arka.prava.util.utils

import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import com.arka.prava.util.interfaces.Provider

fun Fragment.showAlertDialog(s: String) {
    AlertDialog.Builder(requireContext())
        .setMessage(s)
        .create()
        .show()
}

fun Fragment.showToast(msg: String, length: Int = Toast.LENGTH_SHORT) {
    Toast.makeText(requireContext(), msg, length).show()
}

/**
 * Use this to get anything from parent fragment in the following priority
 *
 *  1. Parent fragment has implemented [clazz]
 *  2. Parent fragment has implemented [Provider] with handling for [clazz]
 *  3. Parent activity has implemented [clazz]
 *  4. Parent activity has implemented [Provider] with handling for [clazz]
 */
inline fun <reified T> Fragment.getFromParent(clazz: Class<T>): T? {
    return parentFragment as? T
        ?: (parentFragment as? Provider)?.get(clazz)
        ?: context as? T
        ?: (context as? Provider)?.get(clazz)
}
