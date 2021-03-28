package com.arka.prava.util.auth.models

import com.arka.prava.util.auth.models.GrantType

/**
 * Created by Sidharth Sethia on 10/07/20.
 * Copyright (c) 2020 UltraHuman HealthCare Pvt Ltd. All rights reserved.
 */
data class LoginRequestData(
    val grantType: GrantType,
    val token: String? = null,
    val userId: String? = null,
    val email: String? = null,
    val name: String? = null,
    val data: Any? = null
)
