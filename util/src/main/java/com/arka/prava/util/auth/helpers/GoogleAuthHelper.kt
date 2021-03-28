package com.arka.prava.util.auth.helpers

import android.app.Activity
import android.content.Context
import android.content.Intent
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.ConnectionResult
import com.google.android.gms.common.GoogleApiAvailability
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.tasks.Task
import com.arka.prava.util.auth.LoginKit
import com.arka.prava.util.auth.callbacks.LoginGrantCallback
import com.arka.prava.util.auth.models.GrantType
import com.arka.prava.util.auth.models.LoginError
import com.arka.prava.util.auth.models.LoginErrorResult
import com.arka.prava.util.auth.models.LoginRequestData

/**
 * Created by Sidharth Sethia on 13/07/20.
 * Copyright (c) 2020 UltraHuman HealthCare Pvt Ltd. All rights reserved.
 */
class GoogleAuthHelper constructor(applicationContext: Context, private val callback: LoginGrantCallback) {

    companion object {
        internal const val RC_SIGN_IN = 0x9001
    }

    private val signInClient: GoogleSignInClient
    private val googleApiAvailability = GoogleApiAvailability.getInstance()

    init {
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(LoginKit.getInstance().googleRequestId)
            .requestEmail()
            .requestProfile()
            .build()
        signInClient = GoogleSignIn.getClient(applicationContext, gso)
    }

    internal fun login(activity: Activity) {
        initiateLoginRequest(activity)
    }

    private fun initiateLoginRequest(activity: Activity) {
        // check if Google Play Services API available
        val resultCode = googleApiAvailability.isGooglePlayServicesAvailable(activity)
        when {
            resultCode == ConnectionResult.SUCCESS -> signOutAndLogin(activity)
            googleApiAvailability.isUserResolvableError(resultCode) -> {
                googleApiAvailability.getErrorDialog(activity, resultCode, 0).show()
            }
            else -> callback.onError(
                LoginErrorResult(
                    error = LoginError.API_ERROR,
                    data = "google login initiate error$resultCode"
                )
            )
        }
    }

    private fun signOutAndLogin(activity: Activity) {
        // log out any existing user before starting login process
        if (GoogleSignIn.getLastSignedInAccount(activity) != null) {
            signInClient.signOut().addOnCompleteListener {
                if (!it.isSuccessful) {
                    callback.onError(LoginErrorResult(LoginError.EXCEPTION, it.exception))
                } else {
                    requestLogin(activity, callback)
                }
            }
        } else {
            requestLogin(activity, callback)
        }
    }

    private fun requestLogin(activity: Activity, callback: LoginGrantCallback) {
        callback.onStart()
        val signInIntent = signInClient.signInIntent
        activity.startActivityForResult(signInIntent, RC_SIGN_IN)
    }

    fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        takeIf { requestCode == RC_SIGN_IN }?.run {
            when {
                resultCode == Activity.RESULT_OK && data != null -> {
                    GoogleSignIn.getSignedInAccountFromIntent(data).run {
                        handleSignInResult(this)
                    }
                }
                resultCode == Activity.RESULT_CANCELED -> {
                    callback.onError(
                        LoginErrorResult(
                            error = LoginError.USER_CANCELLED,
                            data = "google login, User canceled"
                        )
                    )
                }
            }
        }
    }

    private fun handleSignInResult(task: Task<GoogleSignInAccount>) {
        try {
            task.getResult(ApiException::class.java)?.run {
                if (idToken.isNullOrBlank()) {
                    callback.onError(
                        LoginErrorResult(
                            error = LoginError.NULL_TOKEN,
                            data = "google login, null token"
                        )
                    )
                } else {
                    callback.onSuccess(loginRequestData())
                }
            } ?: kotlin.run {
                callback.onError(
                    LoginErrorResult(
                        error = LoginError.NULL_RESULT,
                        data = "google login, null result"
                    )
                )
            }
        } catch (e: ApiException) {
            callback.onError(LoginErrorResult(LoginError.EXCEPTION, e))
        }
    }

    private fun GoogleSignInAccount.loginRequestData(): LoginRequestData {
        return LoginRequestData(
            GrantType.GOOGLE,
            token = idToken,
            userId = id,
            name = displayName,
            email = email
        )
    }
}
