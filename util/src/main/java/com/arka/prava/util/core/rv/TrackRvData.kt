package com.arka.prava.util.core.rv

/**
 * Created by Sidharth Sethia on 26/06/20.
 * Copyright (c) 2020 UltraHuman HealthCare Pvt Ltd. All rights reserved.
 */
interface TrackRvData {
    var tracked: Boolean
    fun onTracked() {
        tracked = false // We want impression to be tracked for every scroll
    }
}
