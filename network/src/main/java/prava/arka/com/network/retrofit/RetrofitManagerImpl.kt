package prava.arka.com.network.retrofit

import com.google.gson.Gson
import prava.arka.com.network.model.HttpClientConfig
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

internal class RetrofitManagerImpl(
    private val config: HttpClientConfig,
    private val gson: Gson,
    private val httpClient: OkHttpClient
) : RetrofitManager {
    override fun getRetrofitInstance(): Retrofit {
        return Retrofit.Builder().run {
            baseUrl(config.baseUrl)
            addConverterFactory(GsonConverterFactory.create(gson))
            client(httpClient)
            build()
        }
    }
}
