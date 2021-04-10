package prava.arka.com.network.model

import okhttp3.Interceptor
import okhttp3.Response

data class HttpClientConfig(
    val connectTimeOutInSec: Long = 30L,
    val readTimeoutInSec: Long = 30L,
    val writeTimeoutInSec: Long = 30L,
    val baseUrl: String,
    val headers: Map<String, String>?,
    val dynamicHeaders: () -> Map<String, String>?, // provide these for headers which may change dynamically during a product flow
    val interceptors: List<(chain: Interceptor.Chain) -> Response>?
)
