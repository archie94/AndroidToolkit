package com.prava.arka.common.storage

import androidx.annotation.ColorInt
import androidx.annotation.ColorRes
import androidx.annotation.DimenRes
import androidx.annotation.StringRes

/**
 * Created by Arka Prava Basu<arkaprava94@gmail.com> on 2019-07-09
 **/
interface ISharedPreferenceManager {
    fun getString(@StringRes id: Int): String

    fun getString(@StringRes id: Int, vararg obj: Any): String

    fun getDimensionPixelOffset(@DimenRes dimen: Int): Int

    @ColorInt
    fun getColor(@ColorRes colorRes: Int): Int
}
