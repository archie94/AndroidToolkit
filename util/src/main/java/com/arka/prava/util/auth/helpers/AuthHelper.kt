package com.arka.prava.util.auth.helpers

import com.arka.prava.util.auth.callbacks.AppLoginCallback
import com.arka.prava.util.auth.models.LoginRequestData

/**
 * Created by Sidharth Sethia on 10/07/20.
 * Copyright (c) 2020 UltraHuman HealthCare Pvt Ltd. All rights reserved.
 */
interface AuthHelper {
    fun login(data: LoginRequestData, callback: AppLoginCallback)
}
