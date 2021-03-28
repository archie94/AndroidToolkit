package com.arka.prava.util.auth

import android.app.Activity
import android.content.Intent
import com.arka.prava.util.auth.callbacks.LoginGrantCallback
import com.arka.prava.util.auth.callbacks.LoginRequestCallback
import com.arka.prava.util.auth.callbacks.LoginViewCallback
import com.arka.prava.util.auth.callbacks.LoginViewGrantCallback
import com.arka.prava.util.auth.helpers.AuthHelper
import com.arka.prava.util.auth.helpers.FacebookAuthHelper
import com.arka.prava.util.auth.helpers.GoogleAuthHelper
import com.arka.prava.util.auth.models.GrantType
import com.arka.prava.util.auth.models.LoginErrorResult
import com.arka.prava.util.auth.models.LoginRequestData
import java.lang.ref.WeakReference

/**
 * Created by Sidharth Sethia on 10/07/20.
 * Copyright (c) 2020 com.arka.prava.util HealthCare Pvt Ltd. All rights reserved.
 */

class LoginViewDelegate(
    private val activity: Activity,
    private val authHelper: AuthHelper,
    private val loginViewCallback: LoginViewCallback
) : LoginViewGrantCallback {

    private val facebookAuthHelper = FacebookAuthHelper()
    private val googleAuthHelper = GoogleAuthHelper(activity.applicationContext, loginGrantCallback(
        GrantType.GOOGLE))

    fun facebookLogin() {
        facebookAuthHelper.login(activity, loginGrantCallback(GrantType.FACEBOOK))
    }

    fun googleLogin() {
        googleAuthHelper.login(activity)
    }

    fun guestLogin() {
        onLoginGranted(LoginRequestData(GrantType.GUEST))
    }

    override fun onLoginStart(type: GrantType) {
        loginViewCallback.onLoginStart(type)
    }

    override fun onLoginError(type: GrantType, errorResult: LoginErrorResult) {
        loginViewCallback.onLoginError(type, errorResult)
    }

    override fun onLoginGranted(requestData: LoginRequestData) {
        authHelper.login(requestData, loginRequestCallback(requestData.grantType))
    }

    private fun loginRequestCallback(type: GrantType): LoginRequestCallback {
        return LoginRequestCallback(type, WeakReference(loginViewCallback))
    }

    private fun loginGrantCallback(type: GrantType): LoginGrantCallback {
        return LoginGrantCallback(type, WeakReference(this))
    }

    fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode == GoogleAuthHelper.RC_SIGN_IN) {
            googleAuthHelper.onActivityResult(requestCode, resultCode, data)
        } else {
            facebookAuthHelper.onActivityResult(requestCode, resultCode, data)
        }
    }
}
