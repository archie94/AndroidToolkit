package com.arka.prava.util.core.rv

import android.view.View
import androidx.recyclerview.widget.RecyclerView

/**
 * Created by Arka Prava Basu <arkaprava94@gmail.com> on 18/8/19.
 */
abstract class RvViewHolder<T : IRvData>(
    itemView: View
) : RecyclerView.ViewHolder(itemView) {
    abstract fun bind(data: T, position: Int)
    open fun onRebind(data: T, position: Int, payloads: MutableList<Any>) { }
}
