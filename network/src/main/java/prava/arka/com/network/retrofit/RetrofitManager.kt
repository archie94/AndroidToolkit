package prava.arka.com.network.retrofit

import retrofit2.Retrofit

internal interface RetrofitManager {
    fun getRetrofitInstance(): Retrofit
}
