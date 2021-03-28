package com.arka.prava.util.utils

import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import com.squareup.picasso.Picasso
import com.squareup.picasso.Target
import kotlinx.coroutines.CancellableContinuation
import kotlinx.coroutines.suspendCancellableCoroutine
import java.lang.Exception
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException

private const val IGNORE_BITMAP_DIMENS = -1

suspend fun getBitmapFromUrl(
    url: String,
    targetHeight: Int = IGNORE_BITMAP_DIMENS,
    targetWidth: Int = IGNORE_BITMAP_DIMENS
): Bitmap? {
    return suspendCancellableCoroutine { cont ->
        Picasso.get().load(url).apply {
            if (targetHeight != IGNORE_BITMAP_DIMENS && targetWidth != IGNORE_BITMAP_DIMENS) {
                resize(targetWidth, targetHeight).centerCrop()
            }
        }.into(BitmapTarget(url, cont))
    }
}

private class BitmapTarget(
    private val url: String,
    private val cont: CancellableContinuation<Bitmap?>
) : Target {
    override fun onPrepareLoad(placeHolderDrawable: Drawable?) {
    }

    override fun onBitmapFailed(e: Exception?, errorDrawable: Drawable?) {
        if (e != null) {
            cont.resumeWithException(e)
        }
    }

    override fun onBitmapLoaded(bitmap: Bitmap?, from: Picasso.LoadedFrom?) {
        cont.resume(bitmap)
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as BitmapTarget

        if (url != other.url) return false

        return true
    }

    override fun hashCode(): Int {
        return url.hashCode()
    }
}
