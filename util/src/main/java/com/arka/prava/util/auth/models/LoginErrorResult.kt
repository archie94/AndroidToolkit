package com.arka.prava.util.auth.models

import com.arka.prava.util.auth.models.LoginError

/**
 * Created by Sidharth Sethia on 10/07/20.
 * Copyright (c) 2020 UltraHuman HealthCare Pvt Ltd. All rights reserved.
 */
data class LoginErrorResult(
    val error: LoginError,
    val data: Any? = null
)
