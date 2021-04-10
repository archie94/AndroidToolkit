package prava.arka.com.network

import prava.arka.com.network.di.NetworkComponent
import prava.arka.com.network.model.HttpClientConfig

interface NetworkSDK {
    fun getNetworkComponent(
        config: HttpClientConfig
    ): NetworkComponent
}
