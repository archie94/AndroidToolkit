package com.arka.prava.util.auth.callbacks

import com.arka.prava.util.auth.models.GrantType
import com.arka.prava.util.auth.models.LoginErrorResult
import java.lang.ref.WeakReference

/**
 * Created by Sidharth Sethia on 10/07/20.
 * Copyright (c) 2020 UltraHuman HealthCare Pvt Ltd. All rights reserved.
 */
internal class LoginRequestCallback(
    private val type: GrantType,
    private val activityWeakRef: WeakReference<LoginViewCallback>
) : AppLoginCallback {

    override fun onStart() {
    }

    override fun onError(errorResult: LoginErrorResult) {
        activityWeakRef.get()?.onLoginError(type, errorResult)
    }

    override fun onSuccess(data: IAuthDetails) {
        activityWeakRef.get()?.onLoginSuccess(type, data)
    }
}
