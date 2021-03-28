package com.arka.prava.util.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

/**
 * Created by Arka Prava Basu<arkaprava94@gmail.com> on 2019-08-04
 **/
class BaseViewModelFactory<T>(private val creator: () -> T) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T = creator.invoke() as T
}
