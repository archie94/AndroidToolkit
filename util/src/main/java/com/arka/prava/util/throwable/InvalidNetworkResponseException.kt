package com.arka.prava.util.throwable

/**
 * A [RuntimeException] denoting an invalid api response
 *
 * Created by Arka Prava Basu <arkaprava94@gmail.com> on 10/28/20.
 **/
class InvalidNetworkResponseException(msg: String? = null) : RuntimeException(msg)
