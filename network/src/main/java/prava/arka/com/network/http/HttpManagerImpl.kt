package prava.arka.com.network.http

import prava.arka.com.network.BuildConfig
import prava.arka.com.network.model.HttpClientConfig
import okhttp3.Headers
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import java.util.concurrent.TimeUnit

internal class HttpManagerImpl(
    private val config: HttpClientConfig
) : HttpManager {

    private val logging = HttpLoggingInterceptor()

    private val clientBuilder: OkHttpClient.Builder = OkHttpClient.Builder().apply {
        connectTimeout(config.connectTimeOutInSec, TimeUnit.SECONDS)
        readTimeout(config.readTimeoutInSec, TimeUnit.SECONDS)
        writeTimeout(config.writeTimeoutInSec, TimeUnit.SECONDS)
        addInterceptor { chain ->
            val headerBuilder = Headers.Builder()
            config.headers?.forEach {
                headerBuilder.add(it.key, it.value)
            }
            config.dynamicHeaders.invoke()?.forEach {
                headerBuilder.add(it.key, it.value)
            }
            chain.request().headers.forEach {
                headerBuilder.add(it.first, it.second)
            }
            val original = chain.request()
            val request = original.newBuilder()
                .headers(headerBuilder.build())
                .build()
            chain.proceed(request)
        }
        initializeLogger(this)
        initializeCookieJar(this)
        initializeInterceptors(this)
    }

    private fun initializeInterceptors(builder: OkHttpClient.Builder) {
        config.interceptors?.forEach { builder.addInterceptor(it) }
    }

    private fun initializeCookieJar(builder: OkHttpClient.Builder) {
//        val cookieManager = CookieManager().apply { setCookiePolicy(CookiePolicy.ACCEPT_ALL) }
//        builder.cookieJar(JavaNetCookieJar(cookieManager))
    }

    private fun initializeLogger(builder: OkHttpClient.Builder) {
        if (BuildConfig.DEBUG) {
            logging.level = HttpLoggingInterceptor.Level.BODY
            builder.addInterceptor(logging)
        }
    }

    override fun getHttpClient(): OkHttpClient = clientBuilder.build()
}
