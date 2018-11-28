package prava.arka.com.network

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import java.util.concurrent.TimeUnit

/**
 * Created by Arka Prava Basu <arkaprava94@gmail.com> on 28/11/18.
 */

private const val CONNECT_TIMEOUT_IN_SEC = 30
private const val READ_TIMEOUT_IN_SEC = 30
private const val WRITE_TIMEOUT_IN_SEC = 30

class HTTPManager {
    companion object {

        private val logging = HttpLoggingInterceptor()

        fun getClientBuilder(): OkHttpClient.Builder {
            val normalBuilder = OkHttpClient.Builder()
            normalBuilder.connectTimeout(CONNECT_TIMEOUT_IN_SEC.toLong(), TimeUnit.SECONDS)
            normalBuilder.readTimeout(READ_TIMEOUT_IN_SEC.toLong(), TimeUnit.SECONDS)
            normalBuilder.writeTimeout(WRITE_TIMEOUT_IN_SEC.toLong(), TimeUnit.SECONDS)
            initializeLogger(normalBuilder)
            return normalBuilder
        }

        private fun initializeLogger(builder: OkHttpClient.Builder) {
            if (BuildConfig.DEBUG) {
                logging.level = HttpLoggingInterceptor.Level.BODY
                builder.addInterceptor(logging)
            }
        }
    }
}
