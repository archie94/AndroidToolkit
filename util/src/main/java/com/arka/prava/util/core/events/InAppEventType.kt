package com.arka.prava.util.core.events

/**
 * Created by Sidharth Sethia on 15/06/20.
 * Copyright (c) 2020 UltraHuman HealthCare Pvt Ltd. All rights reserved.
 */

interface InAppEventType {
    val key: String?
        get() = this::class.java.canonicalName
}
