package com.arka.prava.util.auth.callbacks

import com.arka.prava.util.auth.models.LoginErrorResult

/**
 * Created by Sidharth Sethia on 10/07/20.
 * Copyright (c) 2020 com.arka.prava.util HealthCare Pvt Ltd. All rights reserved.
 */

interface LoginCallback<T> {
    fun onStart()
    fun onError(errorResult: LoginErrorResult)
    fun onSuccess(data: T)
}
