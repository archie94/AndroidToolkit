package com.arka.prava.util.auth.callbacks

import com.arka.prava.util.auth.models.GrantType
import com.arka.prava.util.auth.models.LoginErrorResult

/**
 * Created by Sidharth Sethia on 15/07/20.
 * Copyright (c) 2020 UltraHuman HealthCare Pvt Ltd. All rights reserved.
 */
interface LoginViewCallback {
    fun onLoginStart(type: GrantType)
    fun onLoginError(type: GrantType, errorResult: LoginErrorResult)
    fun onLoginSuccess(type: GrantType, data: IAuthDetails)
}
