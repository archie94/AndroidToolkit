package com.prava.arka.common.util

/**
 * Created by Arka Prava Basu <arkaprava94@gmail.com> on 23/9/19.
 */

/**
 * Applies the given [transform] function to each element of the original collection
 * and appends the results to the given [destination].
 */
inline fun <T, R, C : MutableCollection<in R>> Iterable<T>.mapToMultiple(destination: C, transform: (T) -> Collection<R>): C {
    for (item in this)
        destination.addAll(transform(item))
    return destination
}
