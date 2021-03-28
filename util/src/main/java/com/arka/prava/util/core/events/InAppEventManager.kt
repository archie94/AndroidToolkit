package com.arka.prava.util.core.events

import androidx.lifecycle.Observer

/**
 * Created by Sidharth Sethia on 15/06/20.
 * Copyright (c) 2020 UltraHuman HealthCare Pvt Ltd. All rights reserved.
 */

interface InAppEventManager {
    fun addObserver(type: InAppEventType, observer: Observer<InAppEvent>)

    fun removeObserver(type: InAppEventType, observer: Observer<InAppEvent>)

    fun sendEvent(event: InAppEvent)
}
