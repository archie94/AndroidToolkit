package prava.arka.com.network

import com.google.gson.GsonBuilder
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

/**
 * Created by Arka Prava Basu <arkaprava94@gmail.com> on 28/11/18.
 */
class RetrofitHelper {

    companion object {
        private val gson = GsonBuilder().excludeFieldsWithoutExposeAnnotation().setLenient().create()
        private var retrofit: Retrofit? = null
        private val logging = HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)
        private val httpClient = HTTPManager.getClientBuilder()

        fun <S> createRetrofitService(serviceClass: Class<S>): S {
            return createService(serviceClass)
        }

        private fun <S> createService(serviceClass: Class<S>): S {
            synchronized(RetrofitHelper::class.java) {
                if (retrofit == null) {
                    val builder = Retrofit.Builder()
                        .baseUrl(NetworkKit.getInstance().getBaseUrl())
                        .addConverterFactory(GsonConverterFactory.create(gson))
                        .client(
                            if (BuildConfig.DEBUG)
                                httpClient.addInterceptor(logging).build()
                            else
                                httpClient.build()
                        )

                    retrofit = builder.build()
                }
                return retrofit!!.create(serviceClass)
            }
        }
    }
}
