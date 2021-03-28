package com.arka.prava.util.auth

import android.annotation.SuppressLint
import com.arka.prava.util.auth.helpers.FacebookAuthHelper

/**
 * Created by Sidharth Sethia on 10/07/20.
 * Copyright (c) 2020 com.arka.prava.util HealthCare Pvt Ltd. All rights reserved.
 */
class LoginKit private constructor(
    internal val googleRequestId: String
) {
    companion object {
        @SuppressLint("StaticFieldLeak")
        private var instance: LoginKit? = null

        fun init(googleRequestId: String) {
            instance = LoginKit(googleRequestId)
        }

        internal fun getInstance(): LoginKit {
            return instance ?: throw Exception("Login kit not initialized")
        }

        fun logout() {
            FacebookAuthHelper.logOutFacebook()
        }
    }
}
