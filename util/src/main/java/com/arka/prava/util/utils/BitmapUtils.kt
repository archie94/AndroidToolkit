package com.arka.prava.util.utils

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Matrix

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

fun ByteArray.toBitmap(): Bitmap? {
    return BitmapFactory.decodeByteArray(this, 0, this.size)
}
