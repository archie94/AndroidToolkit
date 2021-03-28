package com.arka.prava.util.core.events

import androidx.lifecycle.Observer
import java.util.concurrent.CopyOnWriteArrayList

/**
 * Created by Sidharth Sethia on 15/06/20.
 * Copyright (c) 2020 UltraHuman HealthCare Pvt Ltd. All rights reserved.
 */

class InAppEventManagerImpl : InAppEventManager {

    private val observables: HashMap<String, CopyOnWriteArrayList<Observer<InAppEvent>>> = HashMap()

    override fun addObserver(type: InAppEventType, observer: Observer<InAppEvent>) {
        checkIfNull(type)

        observables[type.key]?.apply {
            if (!contains(observer)) {
                add(observer)
            }
        }
    }

    override fun removeObserver(type: InAppEventType, observer: Observer<InAppEvent>) {
        observables[type.key]?.remove(observer)
    }

    override fun sendEvent(event: InAppEvent) {
        observables[event.type.key]?.apply {
            forEach { it.onChanged(event) }
        }
    }

    private fun checkIfNull(type: InAppEventType) {
        if (observables[type.key] == null && type.key != null) {
            observables[type.key!!] = CopyOnWriteArrayList()
        }
    }
}
