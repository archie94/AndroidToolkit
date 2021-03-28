package com.arka.prava.util.utils

/**
 * Represents Class, Field, Function or Property which are experimental in this SDK and
 * may change in future revisions
 */
@Target(
    AnnotationTarget.CLASS,
    AnnotationTarget.FIELD,
    AnnotationTarget.FUNCTION,
    AnnotationTarget.PROPERTY
)
annotation class AppExperimental(
    val msg: String = "Experimental! May change"
)
