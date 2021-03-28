package com.arka.prava.util.logging

import android.util.Log
import com.google.firebase.crashlytics.FirebaseCrashlytics
import com.newrelic.agent.android.NewRelic
import timber.log.Timber

class CrashReportingTree : Timber.Tree() {
    override fun log(priority: Int, tag: String?, message: String, t: Throwable?) {
        if (priority != Log.ERROR) {
            return
        }
        t?.let {
            FirebaseCrashlytics.getInstance().recordException(it)
            NewRelic.recordHandledException(RuntimeException(it))
        } ?: kotlin.run {
            FirebaseCrashlytics.getInstance().recordException(RuntimeException(message))
            NewRelic.recordHandledException(RuntimeException(message))
        }
    }
}
