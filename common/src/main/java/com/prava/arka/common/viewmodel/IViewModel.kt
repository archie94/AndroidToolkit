package com.prava.arka.common.viewmodel

import androidx.lifecycle.LiveData
import com.prava.arka.common.DataLoadState

/**
 * Created by Arka Prava Basu <arkaprava94@gmail.com> on 2/9/19.
 */
interface IViewModel {
    fun getLoadStateChannel(): LiveData<DataLoadState>
}
