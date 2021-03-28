package com.arka.prava.util.model

/**
 * Created by Arka Prava Basu <arkaprava94@gmail.com> on 2/9/19.
 */
sealed class ApiResult<T>

class Success<T>(val data: T) : ApiResult<T>()

/**
 * Use the [Failure.msg] for user-friendly msgs. For technical msgs use [Failure.cause] message
 */
class Failure<T>(val cause: Throwable?, val msg: String? = null, val title: String? = null) : ApiResult<T>()
