package com.arka.prava.util.utils

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.media.ThumbnailUtils
import android.provider.MediaStore
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.File
import java.io.FileOutputStream
import java.io.InputStream

/**
 * Created by Arka Prava Basu <arkaprava94@gmail.com> on 10/22/20.
 **/
fun getThumbnailFromVideoPath(videoPath: String): Bitmap? =
    ThumbnailUtils.createVideoThumbnail(videoPath, MediaStore.Video.Thumbnails.MICRO_KIND)

fun getThumbnailFromImagePath(imagePath: String, thumbSize: Int = 512): Bitmap? =
    ThumbnailUtils.extractThumbnail(
        BitmapFactory.decodeFile(imagePath),
        thumbSize,
        thumbSize
    )

fun Bitmap.saveAsPng(targetFile: File, quality: Int = 100): Boolean {
    if (!targetFile.exists()) {
        targetFile.createNewFile()
    }
    return compress(Bitmap.CompressFormat.PNG, quality, FileOutputStream(targetFile))
}

suspend fun InputStream.saveAsFile(targetFile: File, fileType: String): Boolean {
    return withContext(Dispatchers.IO) {
        this@saveAsFile.copyTo(
            FileOutputStream(targetFile),
            1024
        )
        true
    }
}

fun String.getFileExtensionFromMimeType() = when (this) {
    "image/bmp" -> "bmp"
    "image/cis-cod" -> "cod"
    "image/gif" -> "gif"
    "image/ief" -> "ief"
    "image/jpeg" -> "jpeg"
    "image/pipeg" -> "jfif"
    "image/svg+xml" -> "svg"
    "image/tiff" -> "tiff"
    "image/x-cmu-raster" -> "ras"
    "image/x-cmx" -> "cmx"
    "image/x-icon" -> "ico"
    "image/x-portable-anymap" -> "pnm"
    "image/x-portable-bitmap" -> "pbm"
    "image/x-portable-graymap" -> "pgm"
    "image/x-portable-pixmap" -> "ppm"
    "image/x-rgb" -> "rgb"
    "image/x-xbitmap" -> "xbm"
    "image/x-xpixmap" -> "xpm"
    "image/x-xwindowdump" -> "xwd"
    "image/png" -> "png"
    else -> null
}
