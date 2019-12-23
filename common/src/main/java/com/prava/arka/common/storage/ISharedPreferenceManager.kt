package com.prava.arka.common.storage

/**
 * Created by Arka Prava Basu<arkaprava94@gmail.com> on 2019-07-09
 **/
interface ISharedPreferenceManager {
    fun getBoolean(key: String, defValue: Boolean): Boolean

    fun putBoolean(key: String, value: Boolean)

    fun getString(key: String, defValue: String?): String?

    fun putString(key: String, value: String?)
}
