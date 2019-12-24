package prava.arka.com.ui

import android.view.View

/**
 * Created by Arka Prava Basu<arkaprava94@gmail.com> on 2019-11-12
 **/
interface ViewData

fun ViewData?.getVisibility(hideView: Boolean = true): Int =
    if (this == null) {
        if (hideView)
            View.GONE
        else
            View.INVISIBLE
    } else {
        View.VISIBLE
    }
