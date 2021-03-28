package com.arka.prava.util.auth.models

/**
 * Created by Sidharth Sethia on 25/08/20.
 * Copyright (c) 2020 UltraHuman HealthCare Pvt Ltd. All rights reserved.
 */
enum class UserAccessType(val type: String) {
    WHITELISTED("whitelisted"),
    DEFAULT("default")
}
