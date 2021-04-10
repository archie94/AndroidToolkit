package prava.arka.com.network.http

import okhttp3.OkHttpClient

internal interface HttpManager {
    fun getHttpClient(): OkHttpClient
}
