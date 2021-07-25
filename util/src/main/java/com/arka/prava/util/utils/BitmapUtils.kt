package com.arka.prava.util.utils

import android.graphics.Bitmap
import android.graphics.Color
import android.graphics.Matrix
import timber.log.Timber

/**
 * Created by Arka Prava Basu <arkaprava94@gmail.com> on 2/17/21.
 **/
fun Bitmap.flip(): Bitmap {
    // create new matrix for transformation
    val matrix = Matrix()
    matrix.preScale(-1.0f, 1.0f)

    // return transformed image
    return Bitmap.createBitmap(this, 0, 0, this.width, this.height, matrix, true)
}

// http://www.java2s.com/example/android-utility-method/bitmap-color-change/changehue-bitmap-bmp-int-hue-int-width-int-height-4e2b3.html
fun Bitmap?.changeHue(
    hue: Int, width: Int,
    height: Int
): Bitmap? {
    if (this == null) {
        return null
    }
    // int srcwidth = bmp.getWidth();
    // int srcheight = bmp.getHeight();
    // create output bitmap

    // create a mutable empty bitmap
    val bitmap = this.copy(Bitmap.Config.ARGB_8888, true)
//        Bitmap.createBitmap(this, 0, 0, this.width, this.height)
    if (hue < 0 || hue > 360) {
        return null
    }
    val size = width * height
    val allPixels = IntArray(size)
    val top = 0
    val left = 0
    val offset = 0
    bitmap.getPixels(allPixels, offset, width, top, left, width, height)
    var pixel = 0
    var alpha = 0
    val hsv = FloatArray(3)
    for (i in 0 until size) {
        pixel = allPixels[i]
        alpha = Color.alpha(pixel)
        Color.colorToHSV(pixel, hsv)

        // You could specify target color including Saturation for
        // more precise results
        hsv[0] = hue.toFloat()
        hsv[1] = 0.2f
        allPixels[i] = Color.HSVToColor(alpha, hsv)
    }
    Timber.d("x = $top, y = $left, width = $width, height = $height")
    Timber.d("bitmap = ${bitmap.width}, ${bitmap.height}")
    bitmap.isMutable
    bitmap.setPixels(allPixels, offset, width, top, left, width, height)
    return bitmap
}
