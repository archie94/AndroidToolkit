package com.arka.prava.util.utils

import android.view.View

/**
 * Created by Arka Prava Basu<arkaprava94@gmail.com> on 2019-08-08
 **/
fun CharSequence?.getVisibility(hideView: Boolean = true): Int =
    if (this == null || this.trim().isEmpty()) {
        if (hideView)
            View.GONE
        else
            View.INVISIBLE
    } else {
        View.VISIBLE
    }
