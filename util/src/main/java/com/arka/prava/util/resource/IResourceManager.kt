package com.arka.prava.util.resource

import android.graphics.drawable.Drawable
import androidx.annotation.ColorInt
import androidx.annotation.ColorRes
import androidx.annotation.DimenRes
import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import java.io.InputStream

/**
 * Created by Arka Prava Basu<arkaprava94@gmail.com> on 2019-08-04
 **/
interface IResourceManager {
    fun getString(@StringRes id: Int): String

    fun getString(@StringRes id: Int, vararg obj: Any): String

    fun getDimensionPixelOffset(@DimenRes dimen: Int): Int

    @ColorInt
    fun getColor(@ColorRes colorRes: Int): Int

    fun getDimension(@DimenRes dimen: Int): Float

    fun getDrawable(@DrawableRes drawableRes: Int): Drawable

    fun getAsset(fileName: String): InputStream
}
