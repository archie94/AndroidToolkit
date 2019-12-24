package com.prava.arka.common.util

/**
 * Created by Arka Prava Basu<arkaprava94@gmail.com> on 2019-11-04
 **/
fun <T> MutableList<T>.replaceWith(newData: T, predicate: (T) -> Boolean) {
    var pos = -1
    for (i in 0 until this.size) {
        if (predicate.invoke(get(i))) {
            pos = i
            break
        }
    }
    if (pos != -1) {
        removeAt(pos)
        add(pos, newData)
    }
}

fun <T> MutableList<T>.findAndReplaceWith(predicate: (T) -> Boolean, getNewData: (T) -> T) {
    var pos = -1
    for (i in 0 until this.size) {
        if (predicate.invoke(get(i))) {
            pos = i
            break
        }
    }
    if (pos != -1) {
        val oldData = removeAt(pos)
        add(pos, getNewData.invoke(oldData))
    }
}
