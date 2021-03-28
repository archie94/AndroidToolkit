package com.arka.prava.util.utils

/**
 * Similar to [androidx.annotation.WorkerThread] but applies to Kotlin properties too
 */
@Retention(AnnotationRetention.SOURCE)
@Target(
    AnnotationTarget.CLASS,
    AnnotationTarget.FIELD,
    AnnotationTarget.FUNCTION,
    AnnotationTarget.PROPERTY,
    AnnotationTarget.CONSTRUCTOR
)
annotation class AppWorkerThread
