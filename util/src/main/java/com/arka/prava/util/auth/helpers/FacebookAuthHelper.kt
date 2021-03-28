package com.arka.prava.util.auth.helpers

import android.app.Activity
import android.content.Intent
import com.facebook.AccessToken
import com.facebook.CallbackManager
import com.facebook.FacebookCallback
import com.facebook.FacebookException
import com.facebook.login.LoginManager
import com.facebook.login.LoginResult
import com.arka.prava.util.auth.callbacks.LoginCallback
import com.arka.prava.util.auth.callbacks.LoginGrantCallback
import com.arka.prava.util.auth.models.GrantType
import com.arka.prava.util.auth.models.LoginError
import com.arka.prava.util.auth.models.LoginErrorResult
import com.arka.prava.util.auth.models.LoginPermissionError
import com.arka.prava.util.auth.models.LoginRequestData

/**
 * Created by Sidharth Sethia on 10/07/20.
 * Copyright (c) 2020 UltraHuman HealthCare Pvt Ltd. All rights reserved.
 */
internal class FacebookAuthHelper {

    companion object {
        private const val EMAIL_PERMISSION = "email"
        private const val PUBLIC_PROFILE_PERMISSION = "public_profile"

        private fun isTokenActive() = AccessToken.isCurrentAccessTokenActive()

        private fun getCurrentToken() = AccessToken.getCurrentAccessToken()

        private fun getFacebookAccessTokenPermissions(): Set<String>? =
            getCurrentToken().permissions

        private fun getLoginManager() = LoginManager.getInstance()

        fun logOutFacebook() = getLoginManager().logOut()
    }

    private val permissionSet = setOf(EMAIL_PERMISSION, PUBLIC_PROFILE_PERMISSION)
    private val callbackManager: CallbackManager = CallbackManager.Factory.create()

    internal fun login(activity: Activity, callback: LoginGrantCallback) {
        callback.onStart()
        if (accessTokenWithPermissionsExists(permissionSet)) {
            callback.onSuccess(loginRequestData())
        } else {
            requestLogin(activity, callback)
        }
    }

    private fun loginRequestData(): LoginRequestData {
        return LoginRequestData(
            GrantType.FACEBOOK,
            token = getCurrentToken()?.token,
            userId = getCurrentToken()?.userId,
            data = getFacebookAccessTokenPermissions()
        )
    }

    private fun requestLogin(activity: Activity, callback: LoginGrantCallback) {
        logOutFacebook()
        getLoginManager().apply {
            logInWithReadPermissions(activity, permissionSet)
            registerCallback(
                callbackManager,
                object : FacebookCallback<LoginResult> {
                    override fun onSuccess(result: LoginResult?) {
                        handleLoginResult(result, callback)
                        unregisterCallback(callbackManager)
                    }

                    override fun onCancel() {
                        callback.onError(
                            LoginErrorResult(
                                error = LoginError.USER_CANCELLED,
                                data = "fb login, user cancelled"
                            )
                        )
                        unregisterCallback(callbackManager)
                    }

                    override fun onError(error: FacebookException?) {
                        callback.onError(LoginErrorResult(LoginError.EXCEPTION, error))
                        unregisterCallback(callbackManager)
                    }
                }
            )
        }
    }

    fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        callbackManager.onActivityResult(requestCode, resultCode, data)
    }

    private fun handleLoginResult(result: LoginResult?, callback: LoginCallback<LoginRequestData>) {
        when {
            result == null -> callback.onError(
                LoginErrorResult(
                    error = LoginError.NULL_RESULT,
                    data = "fb login result, null"
                )
            )
            result.accessToken == null -> callback.onError(
                LoginErrorResult(
                    error = LoginError.NULL_TOKEN,
                    data = "fb login result, null token"
                )
            )
            result.recentlyDeniedPermissions.isNotEmpty() -> {
                callback.onError(
                    LoginErrorResult(
                        LoginError.PERMISSION_DENIED,
                        LoginPermissionError(result.recentlyDeniedPermissions)
                    )
                )
            }
            else -> callback.onSuccess(loginRequestData())
        }
    }

    private fun accessTokenWithPermissionsExists(permissionSet: Set<String>): Boolean {
        return takeIf { isTokenActive() }?.run {
            getFacebookAccessTokenPermissions()?.containsAll(permissionSet)
        } ?: false
    }
}
