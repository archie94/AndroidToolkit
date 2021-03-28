package com.arka.prava.util.utils

/**
 * Denotes that the annotated target should only be called on a single thread. The thread may be
 * the main/UI thread or a worker thread. This is to represent that the target instance
 * does not support being called fro different thread during its lifecycle.
 *
 * Typical example can be a GpuDelegate operating on OpenGL.
 *
 * Created by Arka Prava Basu <arkaprava94@gmail.com> on 11/23/20.
 **/
@Target(
    AnnotationTarget.CLASS,
    AnnotationTarget.FIELD,
    AnnotationTarget.FUNCTION,
    AnnotationTarget.PROPERTY
)
annotation class AppSingleThread
