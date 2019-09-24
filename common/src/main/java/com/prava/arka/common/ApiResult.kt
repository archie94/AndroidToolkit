package com.prava.arka.common

/**
 * Created by Arka Prava Basu <arkaprava94@gmail.com> on 2/9/19.
 */
sealed class ApiResult<T>

class Success<T>(val data: T): ApiResult<T>()

class Failure<T>(val cause: Throwable?): ApiResult<T>()
