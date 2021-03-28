package com.arka.prava.util.model

sealed class AlertType<T>

/**
 * Alert for showing loading state
 */
class LoadingAlert<T>(val data: T) : AlertType<T>()

/**
 * Alert for some form of msg
 */
class MsgAlert<T>(val msg: T) : AlertType<T>()
