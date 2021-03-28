package com.arka.prava.util.storage

/**
 * Created by Arka Prava Basu <arkaprava94@gmail.com> on 6/9/19.
 */
interface IAppStorageManager {

    fun getMediaTempDirectoryPath(): String

    fun setBaseUrl(baseUrl: String)

    fun getBaseUrl(): String?

    fun isCleverTapUserInitialized(): Boolean

    fun setCleverTapUserInitialized(isInitialized: Boolean)
}
