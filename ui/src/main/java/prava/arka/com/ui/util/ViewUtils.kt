package prava.arka.com.ui.util

import android.content.res.Resources
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView

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
