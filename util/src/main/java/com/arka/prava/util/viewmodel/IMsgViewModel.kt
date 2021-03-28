package com.arka.prava.util.viewmodel

import androidx.lifecycle.LiveData
import com.arka.prava.util.model.AlertType
import com.arka.prava.util.model.Event

/**
 * Created by Arka Prava Basu<arkaprava94@gmail.com> on 2019-11-19
 **/
interface IMsgViewModel {
    fun getMsgChannel(): LiveData<Event<String>>
}

interface IAlertViewModel {
    fun getAlertChannel(): LiveData<Event<AlertType<String>>>
}
