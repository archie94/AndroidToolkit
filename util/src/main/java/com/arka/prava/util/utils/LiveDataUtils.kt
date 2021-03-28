package com.arka.prava.util.utils

import androidx.lifecycle.MutableLiveData

/**
 * Created by Arka Prava Basu<arkaprava94@gmail.com> on 2019-11-04
 **/
fun <T> MutableLiveData<T>.forceNotify() {
    this.value = this.value
}
