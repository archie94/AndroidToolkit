package com.arka.prava.util.utils

import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment

fun Fragment.showAlertDialog(s: String) {
    AlertDialog.Builder(requireContext())
        .setMessage(s)
        .create()
        .show()
}

fun Fragment.showToast(msg: String, length: Int = Toast.LENGTH_SHORT) {
    Toast.makeText(requireContext(), msg, length).show()
}
