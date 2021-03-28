package com.arka.prava.util.auth.callbacks

import com.arka.prava.util.auth.models.GrantType
import com.arka.prava.util.auth.models.LoginErrorResult
import com.arka.prava.util.auth.models.LoginRequestData
import java.lang.ref.WeakReference

/**
 * Created by Sidharth Sethia on 10/07/20.
 * Copyright (c) 2020 UltraHuman HealthCare Pvt Ltd. All rights reserved.
 */

class LoginGrantCallback(
    val type: GrantType,
    private val ref: WeakReference<LoginViewGrantCallback>
) : LoginCallback<LoginRequestData> {

    override fun onStart() {
        ref.get()?.onLoginStart(type)
    }

    override fun onError(errorResult: LoginErrorResult) {
        ref.get()?.onLoginError(type, errorResult)
    }

    override fun onSuccess(data: LoginRequestData) {
        ref.get()?.onLoginGranted(data)
    }
}
