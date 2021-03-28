package com.arka.prava.util.utils

import android.app.Application
import android.widget.Toast

/**
 * Helper methods related to [Application]
 *
 * Created by Arka Prava Basu <arkaprava94@gmail.com> on 12/1/20.
 **/

fun Application.showToast(msg: String, length: Int = Toast.LENGTH_SHORT) {
    Toast.makeText(this, msg, length).show()
}
