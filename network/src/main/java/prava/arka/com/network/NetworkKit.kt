package prava.arka.com.network

import java.lang.RuntimeException

/**
 * Created by Arka Prava Basu <arkaprava94@gmail.com> on 28/11/18.
 */
class NetworkKit private constructor(private val server: String) {

    companion object {
        private var networkKit: NetworkKit? = null
        internal lateinit var headers: Map<String, String>

        fun initialize(server: String, headers: Map<String, String> = mapOf()) {
            this.headers = headers
            networkKit = NetworkKit(server)
        }

        fun getInstance(): NetworkKit {
            return networkKit ?: throw RuntimeException("network Kit not initialized")
        }
    }

    fun getBaseUrl(): String {
        return server
    }
}
