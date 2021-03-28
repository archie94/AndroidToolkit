package com.arka.prava.util.utils

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

inline fun <T> List<T>.unique(predicate: (T, T) -> Boolean): List<T> {
    val list = mutableListOf<T>()
    forEach { item1 ->
        if (list.find { predicate.invoke(item1, it) } == null)
            list.add(item1)
    }
    return list
}

inline fun <T> List<T>.toCsv(): String {
    var s = ""
    forEach {
        s += "$it, "
    }
    return s.trim().trim(',')
}
