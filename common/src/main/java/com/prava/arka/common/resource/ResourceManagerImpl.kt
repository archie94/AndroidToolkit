package com.prava.arka.common.resource

import android.content.Context

/**
 * Created by Arka Prava Basu <arkaprava94@gmail.com> on 24/9/19.
 */
class ResourceManagerImpl(
    private val context: Context
) : IResourceManager {
    override fun getString(id: Int): String = context.getString(id)

    override fun getString(id: Int, vararg obj: Any): String = context.getString(id, *obj)

    override fun getDimensionPixelOffset(dimen: Int): Int =
        context.resources.getDimensionPixelOffset(dimen)

    override fun getColor(colorRes: Int): Int =
        context.resources.getColor(colorRes)
}
