package com.arka.prava.util.resource

import android.content.Context
import android.graphics.drawable.Drawable
import java.io.InputStream

/**
 * Created by Arka Prava Basu <arkaprava94@gmail.com> on 24/9/19.
 */
class ResourceManagerImpl(
    applicationContext: Context
) : IResourceManager {

    private val context: Context = applicationContext.applicationContext

    override fun getString(id: Int): String = context.getString(id)

    override fun getString(id: Int, vararg obj: Any): String = context.getString(id, *obj)

    override fun getDimensionPixelOffset(dimen: Int): Int =
        context.resources.getDimensionPixelOffset(dimen)

    override fun getColor(colorRes: Int): Int =
        context.resources.getColor(colorRes)

    override fun getDimension(dimen: Int): Float = context.resources.getDimension(dimen)

    override fun getDrawable(drawableRes: Int): Drawable =
        context.resources.getDrawable(drawableRes)

    override fun getAsset(fileName: String): InputStream =
        context.assets.open(fileName)
}
