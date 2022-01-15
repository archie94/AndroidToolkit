package com.arka.prava.util.analytics

import android.content.Context
import com.arka.prava.util.utils.AppExperimental

@AppExperimental
interface IAnalyticsEngine : IAnalyticsTracker, AutoCloseable {
    fun getIdentifier(): AnalyticsEngineId

    /**
     * To be called on application create
     */
    fun init(context: Context, token: String?)
}
