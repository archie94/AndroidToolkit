package com.prava.arka.common.viewmodel

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob

/**
 * Created by Arka Prava Basu <arkaprava94@gmail.com> on 2/9/19.
 */
abstract class BaseViewModel : ViewModel() {
    protected val job = SupervisorJob()
    protected val uiScope = CoroutineScope(Dispatchers.Main + job)

    override fun onCleared() {
        job.cancel()
        super.onCleared()
    }
}
