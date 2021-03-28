package com.arka.prava.util.utils

import android.app.Activity
import android.view.View
import android.view.inputmethod.InputMethodManager

/**
 * Created by Arka Prava Basu <arkaprava94@gmail.com> on 11/9/20.
 **/
fun Activity.hideKeyboard() {
    val imm: InputMethodManager =
        getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
    // Find the currently focused view, so we can grab the correct window token from it.
    // If no view currently has focus, create a new one, just so we can grab a window token from it
    val view: View = currentFocus ?: View(this)
    imm.hideSoftInputFromWindow(view.windowToken, 0)
}
