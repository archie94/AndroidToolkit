package com.arka.prava.util.utils

import android.content.res.Resources
import android.graphics.Rect
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
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

fun <B : ViewDataBinding> ViewGroup.inflateView(@LayoutRes layoutId: Int): View {
    return DataBindingUtil.inflate<B>(
        LayoutInflater.from(context),
        layoutId,
        this,
        false
    ).root
}

fun <B : ViewDataBinding> ViewGroup.inflate(@LayoutRes layoutId: Int): B {
    return DataBindingUtil.inflate<B>(
        LayoutInflater.from(context),
        layoutId,
        this,
        false
    )
}

fun <B : ViewDataBinding> B.setHeightInRecyclerView(viewHeight: Int) {
    val lp: RecyclerView.LayoutParams = root.layoutParams as RecyclerView.LayoutParams
    lp.height = viewHeight
    root.layoutParams = lp
}

fun View.setHeight(viewHeight: Int) {
    val lp: ViewGroup.MarginLayoutParams = layoutParams as ViewGroup.MarginLayoutParams
    lp.height = viewHeight
    layoutParams = lp
}

fun View.setWidth(viewWidth: Int) {
    val lp: ViewGroup.MarginLayoutParams = layoutParams as ViewGroup.MarginLayoutParams
    lp.width = viewWidth
    layoutParams = lp
}

fun Resources.dp2px(dp: Float): Float {
    val scale: Float = displayMetrics.density
    return dp * scale + 0.5f
}

fun Resources.sp2px(sp: Float): Float {
    val scale: Float = displayMetrics.scaledDensity
    return sp * scale
}
