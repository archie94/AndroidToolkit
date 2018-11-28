package prava.arka.com.network

import java.lang.RuntimeException

/**
 * Created by Arka Prava Basu <arka.basu@zomato.com> on 28/11/18.
 */
class NetworkKit private constructor(private val server: String) {

    companion object {
        private var networkKit: NetworkKit? = null

        fun initialize(server: String) {
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
