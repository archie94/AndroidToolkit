package prava.arka.com.ui.util

import android.view.ViewGroup
import android.widget.ImageView

/**
 * Created by Arka Prava Basu <arka@ultrahuman.com> on 10/28/20.
 **/
fun ImageView.getSizeAppendedUrl(url: String?): String? {
    val widthIdentifier = "width="
    val heightIdentifier = "height="
    val height = when {
        height == ViewGroup.LayoutParams.MATCH_PARENT -> measuredHeight
        height > 0 -> height
        else -> resources.displayMetrics.heightPixels
    }
    val width = when {
        width == ViewGroup.LayoutParams.MATCH_PARENT -> measuredWidth
        width > 0 -> width
        else -> resources.displayMetrics.widthPixels
    }
    return url
        ?.replaceFirst("\\b$widthIdentifier(\\d+)".toRegex(), "$widthIdentifier$width")
        ?.replaceFirst("\\b$heightIdentifier(\\d+)".toRegex(), "$heightIdentifier$height")
}
