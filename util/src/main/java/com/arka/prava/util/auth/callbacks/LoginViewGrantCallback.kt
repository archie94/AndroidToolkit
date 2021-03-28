package com.arka.prava.util.auth.callbacks

import com.arka.prava.util.auth.models.GrantType
import com.arka.prava.util.auth.models.LoginErrorResult
import com.arka.prava.util.auth.models.LoginRequestData

/**
 * Created by Sidharth Sethia on 15/07/20.
 * Copyright (c) 2020 UltraHuman HealthCare Pvt Ltd. All rights reserved.
 */
interface LoginViewGrantCallback {
    fun onLoginStart(type: GrantType)
    fun onLoginError(type: GrantType, errorResult: LoginErrorResult)
    fun onLoginGranted(requestData: LoginRequestData)
}
