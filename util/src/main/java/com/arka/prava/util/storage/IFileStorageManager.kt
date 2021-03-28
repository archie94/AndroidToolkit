package com.arka.prava.util.storage

import com.arka.prava.util.utils.AppExperimental

@AppExperimental
interface IFileStorageManager {
    suspend fun writeToFileInInternalStorage(fileName: String, inputBytes: ByteArray): Boolean

    suspend fun appendToFileInInternalStorage(fileName: String, inputBytes: ByteArray): Boolean

    suspend fun getFileContentFromInternalStorage(fileName: String): ByteArray?

    suspend fun removeDirectoryInInternalStorage(directoryName: String): Boolean
}
