package prava.arka.com.ui

import android.app.Activity
import androidx.appcompat.app.AlertDialog

/**
 * Created by Arka Prava Basu<arkaprava94@gmail.com> on 2019-12-01
 **/
fun Activity.showAlertDialog(s: String) {
    AlertDialog.Builder(this)
        .setMessage(s)
        .create()
        .show()
}
