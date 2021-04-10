package prava.arka.com.network

import prava.arka.com.network.di.DaggerNetworkComponent
import prava.arka.com.network.di.NetworkComponent
import prava.arka.com.network.di.NetworkModule
import prava.arka.com.network.model.HttpClientConfig

class NetworkSdkImpl : NetworkSDK {
    override fun getNetworkComponent(config: HttpClientConfig): NetworkComponent {
        return DaggerNetworkComponent.builder().networkModule(NetworkModule(config)).build()
    }
}
