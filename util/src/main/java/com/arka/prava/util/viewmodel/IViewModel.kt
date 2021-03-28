package com.arka.prava.util.viewmodel

import androidx.lifecycle.LiveData
import com.arka.prava.util.model.DataLoadState

/**
 * Created by Arka Prava Basu <arkaprava94@gmail.com> on 2/9/19.
 */
interface IViewModel {
    fun getLoadStateChannel(): LiveData<DataLoadState>
}
