package com.arka.prava.util.auth.models

/**
 * Created by Sidharth Sethia on 10/07/20.
 * Copyright (c) 2020 UltraHuman HealthCare Pvt Ltd. All rights reserved.
 */

data class User(
    val userId: String,
    val name: String,
    val email: String,
    val grantType: GrantType
)
