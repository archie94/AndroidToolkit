package com.arka.prava.util.throwable

/**
 * A [RuntimeException] denoting an invalid pref data [Ex: blank primary data like userID]
 *
 * Created by sahu<abhistark7@gmail.com> on 09/11/20.
 */
class InvalidPrefDataException(msg: String? = null) : RuntimeException(msg)
