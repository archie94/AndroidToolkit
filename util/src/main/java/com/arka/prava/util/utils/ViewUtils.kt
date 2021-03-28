package com.arka.prava.util.utils

import android.graphics.Rect
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.arka.prava.util.core.rv.ILifecycleAwareVH

fun View.applyMarginBottom(bottomMargin: Int) {
    layoutParams = (layoutParams as? ViewGroup.MarginLayoutParams).also { params ->
        params?.bottomMargin = bottomMargin
    }
}

fun View.applyMarginTop(topMargin: Int) {
    layoutParams = (layoutParams as? ViewGroup.MarginLayoutParams).also { params ->
        params?.topMargin = topMargin
    }
}

fun View.applyMarginStart(startMargin: Int) {
    layoutParams = (layoutParams as? ViewGroup.MarginLayoutParams).also { params ->
        params?.marginStart = startMargin
    }
}

fun View.applyMarginEnd(endMargin: Int) {
    layoutParams = (layoutParams as? ViewGroup.MarginLayoutParams).also { params ->
        params?.marginEnd = endMargin
    }
}

fun View.applyPaddingBottom(bottomPadding: Int) {
    setPadding(
        paddingLeft,
        paddingTop,
        paddingRight,
        bottomPadding
    )
}

fun View.applyPaddingTop(topPadding: Int) {
    setPadding(
        paddingLeft,
        topPadding,
        paddingRight,
        paddingBottom
    )
}

fun RecyclerView.notifyChildOfParentStart() {
    for (i in 0 until childCount) {
        (getChildViewHolder(getChildAt(i)) as? ILifecycleAwareVH)?.onStart()
    }
}

fun RecyclerView.notifyChildOfParentStop() {
    for (i in 0 until childCount) {
        (getChildViewHolder(getChildAt(i)) as? ILifecycleAwareVH)?.onStop()
    }
}

/**
 * Check if the `this` view is contained inside the argument view.
 */
fun View.isInside(view: View): Boolean {
    val selfRect = getRectOnScreen()
    val argRect = view.getRectOnScreen()
    return argRect.right > selfRect.right && argRect.left < selfRect.left &&
        argRect.top < selfRect.top && argRect.bottom > selfRect.bottom
}

/**
 * Get the [Rect] of `this` view on screen
 */
fun View.getRectOnScreen(): Rect {
    val l = IntArray(2)
    getLocationOnScreen(l)
    return Rect(l[0], l[1], l[0] + width, l[1] + height)
}
