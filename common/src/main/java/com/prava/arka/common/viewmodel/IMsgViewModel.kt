package com.prava.arka.common.viewmodel

import androidx.lifecycle.LiveData
import com.prava.arka.common.Event

/**
 * Created by Arka Prava Basu<arkaprava94@gmail.com> on 2019-11-19
 **/
interface IMsgViewModel {
    fun getMsgChannel(): LiveData<Event<String>>
}

interface IAlertViewModel {
    fun getAlertChannel(): LiveData<Event<AlertType<String>>>
}

sealed class AlertType<T>

class LoadingAlert<T>(val data: T) : AlertType<T>()

class MsgAlert<T>(val msg: T) : AlertType<T>()
