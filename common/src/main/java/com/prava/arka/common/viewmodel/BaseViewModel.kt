package com.prava.arka.common.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.prava.arka.common.Event
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob

/**
 * Created by Arka Prava Basu <arkaprava94@gmail.com> on 2/9/19.
 */
abstract class BaseViewModel : ViewModel() {
    protected val job = SupervisorJob()
    protected val uiScope = CoroutineScope(Dispatchers.Main + job)

    protected val toastChannel = MutableLiveData<Event<String>>()
    protected val alertChannelLD = MutableLiveData<Event<AlertType<String>>>()

    override fun onCleared() {
        job.cancel()
        super.onCleared()
    }
}
