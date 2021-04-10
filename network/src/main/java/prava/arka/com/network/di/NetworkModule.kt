package prava.arka.com.network.di

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import prava.arka.com.network.http.HttpManager
import prava.arka.com.network.http.HttpManagerImpl
import prava.arka.com.network.model.HttpClientConfig
import prava.arka.com.network.retrofit.RetrofitManager
import prava.arka.com.network.retrofit.RetrofitManagerImpl
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
internal class NetworkModule(
    private val config: HttpClientConfig
) {

    @Singleton
    @Provides
    fun provideRetrofit(retrofitManager: RetrofitManager): Retrofit {
        return retrofitManager.getRetrofitInstance()
    }

    @Singleton
    @Provides
    fun provideRetrofitManager(gson: Gson, client: OkHttpClient): RetrofitManager {
        return RetrofitManagerImpl(config, gson, client)
    }

    @Singleton
    @Provides
    fun provideHttpClient(httpManager: HttpManager): OkHttpClient = httpManager.getHttpClient()

    @Singleton
    @Provides
    fun provideGson(): Gson = GsonBuilder().excludeFieldsWithoutExposeAnnotation().setLenient().create()

    @Singleton
    @Provides
    fun provideHttpManager(): HttpManager {
        return HttpManagerImpl(config)
    }
}
