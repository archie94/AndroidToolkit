package prava.arka.com.ui.widget

import android.content.Context
import android.content.res.TypedArray
import android.graphics.LinearGradient
import android.graphics.Shader
import android.util.AttributeSet
import prava.arka.com.ui.R

/*
 * Created by Eugene Prytula on 12/1/20.
 * Copyright (c) 2020 MadAppGang. All rights reserved.
 */

class GradientTextView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : androidx.appcompat.widget.AppCompatTextView(context, attrs, defStyleAttr) {

    private val typedArray: TypedArray by lazy { context.obtainStyledAttributes(attrs, R.styleable.GradientTextView) }
    private var colorsArray = 0

    init {
        context.theme.obtainStyledAttributes(attrs, R.styleable.GradientTextView, 0, 0).apply {
            try {
                colorsArray = typedArray.getResourceId(R.styleable.GradientTextView_colors, 0)
            } finally {
                recycle()
            }
        }
    }

    override fun onLayout(changed: Boolean, left: Int, top: Int, right: Int, bottom: Int) {
        super.onLayout(changed, left, top, right, bottom)
        if (changed) {
            val colors = context.resources.getIntArray(colorsArray)
            paint.shader = LinearGradient(0F, 0F, width.toFloat(), height.toFloat(), colors, null, Shader.TileMode.CLAMP)
        }
    }
}
