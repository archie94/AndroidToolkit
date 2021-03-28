package com.arka.prava.util.storage

/**
 * Created by Arka Prava Basu<arkaprava94@gmail.com> on 2019-07-09
 **/
interface ISharedPreferenceManager {
    fun getBoolean(key: String, defValue: Boolean): Boolean

    fun putBoolean(key: String, value: Boolean)

    fun getBooleanSharedPrefLiveData(key: String, value: Boolean): SharedPreferenceBooleanLiveData

    fun getString(key: String, defValue: String?): String?

    fun putString(key: String, value: String?)

    fun getStringSharedPrefLiveData(key: String, defValue: String): SharedPreferenceStringLiveData

    fun getInt(key: String, defValue: Int): Int

    fun putInt(key: String, value: Int)

    fun getLong(key: String, defValue: Long): Long

    fun putLong(key: String, value: Long)

    fun removeKey(key: String)
}
