package com.arka.prava.util.core.rv

import androidx.recyclerview.widget.DiffUtil

/**
 * Created by Arka Prava Basu<arkaprava94@gmail.com> on 2019-12-01
 **/
class DiffUtilItemCallback<T : IRvData> : DiffUtil.ItemCallback<T>() {
    override fun areItemsTheSame(oldItem: T, newItem: T): Boolean =
        (oldItem.getId().isNotEmpty() && (oldItem.getId() == newItem.getId()))

    override fun areContentsTheSame(oldItem: T, newItem: T): Boolean = oldItem == newItem
}
