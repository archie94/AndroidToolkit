package prava.arka.com.ui.widget

import android.content.Context
import android.content.res.TypedArray
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.RectF
import android.graphics.SweepGradient
import android.os.Bundle
import android.os.Parcelable
import android.text.TextPaint
import android.text.TextUtils
import android.util.AttributeSet
import android.util.Log
import android.view.View
import androidx.annotation.ColorInt
import androidx.databinding.BindingMethod
import androidx.databinding.BindingMethods
import prava.arka.com.ui.R
import prava.arka.com.ui.util.dp2px
import prava.arka.com.ui.util.sp2px
import java.text.DecimalFormat

/**
 * Taken and modified from https://github.com/lzyzsd/CircleProgress
 */
@BindingMethods(
    value = [
        BindingMethod(
            type = ArcProgressView::class,
            attribute = "arc_progress",
            method = "setProgress"
        ),
        BindingMethod(
            type = ArcProgressView::class,
            attribute = "arc_finished_start_color",
            method = "setFinishedStrokeStartColor"
        ),
        BindingMethod(
            type = ArcProgressView::class,
            attribute = "arc_finished_end_color",
            method = "setFinishedStrokeEndColor"
        )
    ]
)
class ArcProgressView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) :
    View(context, attrs, defStyleAttr) {

    companion object {
        private const val INSTANCE_STATE = "saved_instance"
        private const val INSTANCE_STROKE_WIDTH = "stroke_width"
        private const val INSTANCE_SUFFIX_TEXT_SIZE = "suffix_text_size"
        private const val INSTANCE_SUFFIX_TEXT_PADDING = "suffix_text_padding"
        private const val INSTANCE_BOTTOM_TEXT_SIZE = "bottom_text_size"
        private const val INSTANCE_BOTTOM_TEXT = "bottom_text"
        private const val INSTANCE_TEXT_SIZE = "text_size"
        private const val INSTANCE_TEXT_COLOR = "text_color"
        private const val INSTANCE_PROGRESS = "progress"
        private const val INSTANCE_MAX = "max"
        private const val INSTANCE_FINISHED_STROKE_START_COLOR = "finished_stroke_start_color"
        private const val INSTANCE_FINISHED_STROKE_END_COLOR = "finished_stroke_end_color"
        private const val INSTANCE_UNFINISHED_STROKE_COLOR = "unfinished_stroke_color"
        private const val INSTANCE_ARC_ANGLE = "arc_angle"
        private const val INSTANCE_SUFFIX = "suffix"
        private const val DEFAULT_SUFFIX_TEXT = "%"
        private const val DEFAULT_ARC_ANGLE = 360 * 0.8f
    }

    private lateinit var paint: Paint
    private lateinit var textPaint: Paint
    private val rectF = RectF()
    private var strokeWidth = 0f
    private var suffixTextSize = 0f
    private var bottomTextSize = 0f
    private var bottomText: String? = null
    private var textSize = 0f
    private var textColor = 0
    private var progress = 0f
    var max = 0
        set(max) {
            if (max > 0) {
                field = max
                invalidate()
            }
        }
    @ColorInt
    private var finishedStrokeStartColor = 0
    @ColorInt
    private var finishedStrokeEndColor = 0
    private var sweepGradient: SweepGradient? = null
    private var unfinishedStrokeColor = 0
    private var arcAngle = 0f
    private var suffixText: String? = "%"
    private var suffixTextPadding = 0f
    private var arcBottomHeight = 0f
    private val defaultFinishedColor = Color.WHITE
    private val defaultUnfinishedColor = Color.rgb(72, 106, 176)
    private val defaultTextColor = Color.rgb(66, 145, 241)
    private val defaultSuffixTextSize: Float
    private val defaultSuffixPadding: Float
    private val defaultBottomTextSize: Float
    private val defaultStrokeWidth: Float
    private val defaultMax = 100
    private val defaultTextSize: Float
    private val minSize: Int

    init {
        minSize = resources.dp2px(100f).toInt()
        defaultTextSize = resources.sp2px(40f)
        defaultSuffixTextSize = resources.sp2px(15f)
        defaultSuffixPadding = resources.dp2px(4f)
        defaultBottomTextSize = resources.sp2px(10f)
        defaultStrokeWidth = resources.dp2px(4f)
        val attributes = context.theme
            .obtainStyledAttributes(attrs, R.styleable.ArcProgress, defStyleAttr, 0)
        initByAttributes(attributes)
        attributes.recycle()
        initPainters()
    }

    private fun initByAttributes(attributes: TypedArray) {
        finishedStrokeStartColor = if (finishedStrokeStartColor == 0) attributes.getColor(
            R.styleable.ArcProgress_arc_finished_start_color,
            defaultFinishedColor
        ) else finishedStrokeStartColor
        finishedStrokeEndColor = if (finishedStrokeEndColor == 0) attributes.getColor(
            R.styleable.ArcProgress_arc_finished_end_color,
            defaultFinishedColor
        ) else finishedStrokeEndColor
        unfinishedStrokeColor = attributes.getColor(
            R.styleable.ArcProgress_arc_unfinished_color,
            defaultUnfinishedColor
        )
        textColor = attributes.getColor(R.styleable.ArcProgress_arc_text_color, defaultTextColor)
        textSize = attributes.getDimension(R.styleable.ArcProgress_arc_text_size, defaultTextSize)
        arcAngle = attributes.getFloat(R.styleable.ArcProgress_arc_angle, DEFAULT_ARC_ANGLE)
        max = attributes.getInt(R.styleable.ArcProgress_arc_max, defaultMax)
        setProgress(attributes.getFloat(R.styleable.ArcProgress_arc_progress, 0f))
        strokeWidth =
            attributes.getDimension(R.styleable.ArcProgress_arc_stroke_width, defaultStrokeWidth)
        suffixTextSize = attributes.getDimension(
            R.styleable.ArcProgress_arc_suffix_text_size,
            defaultSuffixTextSize
        )
        suffixText =
            if (TextUtils.isEmpty(attributes.getString(R.styleable.ArcProgress_arc_suffix_text))) DEFAULT_SUFFIX_TEXT else attributes.getString(
                R.styleable.ArcProgress_arc_suffix_text
            )
        suffixTextPadding = attributes.getDimension(
            R.styleable.ArcProgress_arc_suffix_text_padding,
            defaultSuffixPadding
        )
        bottomTextSize = attributes.getDimension(
            R.styleable.ArcProgress_arc_bottom_text_size,
            defaultBottomTextSize
        )
        bottomText = attributes.getString(R.styleable.ArcProgress_arc_bottom_text)
    }

    private fun initPainters() {
        if (!::textPaint.isInitialized) {
            textPaint = TextPaint()
        }
        textPaint.apply {
            color = textColor
            textSize = this@ArcProgressView.textSize
            isAntiAlias = true
        }
        if (!::paint.isInitialized) {
            paint = Paint()
        }
        paint.apply {
            color = defaultUnfinishedColor
            isAntiAlias = true
            strokeWidth = this@ArcProgressView.strokeWidth
            style = Paint.Style.STROKE
            strokeCap = Paint.Cap.ROUND
        }
    }

    override fun invalidate() {
        initPainters()
        super.invalidate()
    }

    fun getStrokeWidth(): Float {
        return strokeWidth
    }

    fun setStrokeWidth(strokeWidth: Float) {
        this.strokeWidth = strokeWidth
        this.invalidate()
    }

    fun getSuffixTextSize(): Float {
        return suffixTextSize
    }

    fun setSuffixTextSize(suffixTextSize: Float) {
        this.suffixTextSize = suffixTextSize
        this.invalidate()
    }

    fun getBottomText(): String? {
        return bottomText
    }

    fun setBottomText(bottomText: String?) {
        this.bottomText = bottomText
        this.invalidate()
    }

    fun getProgress(): Float {
        return progress
    }

    fun setProgress(progress: Float) {
        this.progress =
            java.lang.Float.valueOf(DecimalFormat("#.##").format(progress.toDouble()))
        if (this.progress > max) {
            this.progress %= max.toFloat()
        }
        invalidate()
    }

    fun getBottomTextSize(): Float {
        return bottomTextSize
    }

    fun setBottomTextSize(bottomTextSize: Float) {
        this.bottomTextSize = bottomTextSize
        this.invalidate()
    }

    fun getTextSize(): Float {
        return textSize
    }

    fun setTextSize(textSize: Float) {
        this.textSize = textSize
        this.invalidate()
    }

    fun getTextColor(): Int {
        return textColor
    }

    fun setTextColor(textColor: Int) {
        this.textColor = textColor
        this.invalidate()
    }

    fun getFinishedStrokeStartColor(): Int {
        return finishedStrokeStartColor
    }

    fun setFinishedStrokeStartColor(finishedStrokeColor: Int) {
        this.finishedStrokeStartColor = finishedStrokeColor
        this.invalidate()
    }

    fun getFinishedStrokeEndColor(): Int {
        return finishedStrokeEndColor
    }

    fun setFinishedStrokeEndColor(finishedStrokeEndColor: Int) {
        this.finishedStrokeEndColor = finishedStrokeEndColor
        this.invalidate()
    }

    fun getUnfinishedStrokeColor(): Int {
        return unfinishedStrokeColor
    }

    fun setUnfinishedStrokeColor(unfinishedStrokeColor: Int) {
        this.unfinishedStrokeColor = unfinishedStrokeColor
        this.invalidate()
    }

    fun getArcAngle(): Float {
        return arcAngle
    }

    fun setArcAngle(arcAngle: Float) {
        this.arcAngle = arcAngle
        this.invalidate()
    }

    fun getSuffixText(): String? {
        return suffixText
    }

    fun setSuffixText(suffixText: String?) {
        this.suffixText = suffixText
        this.invalidate()
    }

    fun getSuffixTextPadding(): Float {
        return suffixTextPadding
    }

    fun setSuffixTextPadding(suffixTextPadding: Float) {
        this.suffixTextPadding = suffixTextPadding
        this.invalidate()
    }

    override fun getSuggestedMinimumHeight(): Int {
        return minSize
    }

    override fun getSuggestedMinimumWidth(): Int {
        return minSize
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        setMeasuredDimension(widthMeasureSpec, heightMeasureSpec)
        val width = MeasureSpec.getSize(widthMeasureSpec)
        rectF[strokeWidth / 2f, strokeWidth / 2f, width - strokeWidth / 2f] =
            MeasureSpec.getSize(heightMeasureSpec) - strokeWidth / 2f
        val radius = width / 2f
        val angle = (360 - arcAngle) / 2f
        arcBottomHeight =
            radius * (1 - Math.cos(angle / 180 * Math.PI)).toFloat()
    }

    private fun setShader(startAngle: Float, endAngle: Float) {
        val gradientColors = intArrayOf(finishedStrokeStartColor, finishedStrokeEndColor)
        val startPosition = startAngle / 360f
        val endPosition = (endAngle - 360) / 360f
        val gradientPositions = floatArrayOf(startPosition, endPosition)
        Log.d("Arc Progress", "startAngle = $startAngle, finishAngle = $endAngle")
        Log.d("Arc Progress", "startPosition = $startPosition, endPosition = $endPosition")
        sweepGradient = SweepGradient(
            measuredWidth.toFloat() / 2,
            measuredHeight.toFloat() / 2,
            gradientColors,
            gradientPositions
        )
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        val startAngle = (270 - arcAngle / 2f)
        val finishedSweepAngle = progress / max.toFloat() * arcAngle
        var finishedStartAngle = startAngle
        if (progress == 0f) finishedStartAngle = 0.01f
        paint.apply {
            shader = null
            color = unfinishedStrokeColor
            strokeWidth = this@ArcProgressView.strokeWidth
        }
        canvas.drawArc(rectF, startAngle, arcAngle, false, paint)
        val cx = measuredWidth.toFloat() / 2
        val cy = measuredHeight.toFloat() / 2
        canvas.rotate(startAngle, cx, cy)
        val gradientColors = intArrayOf(finishedStrokeStartColor, finishedStrokeEndColor)
        val startPosition = 0f
        val endPosition = arcAngle / 360f
        val gradientPositions = floatArrayOf(startPosition, endPosition)
        Log.d("Arc Progress", "startPosition = $startPosition, endPosition = $endPosition")
        // TODO how to change this?
        sweepGradient = SweepGradient(
            cx,
            cy,
            gradientColors,
            gradientPositions
        )
        paint.apply {
            shader = sweepGradient
            color = finishedStrokeStartColor
            strokeWidth = this@ArcProgressView.strokeWidth - resources.dp2px(2f)
        }
        Log.d(
            "Arc Progress",
            "finishedStartAngle = $finishedStartAngle, finishedSweepAngle = $finishedSweepAngle"
        )
        canvas.drawArc(rectF, 0f, finishedSweepAngle, false, paint)
        canvas.rotate(-1 * startAngle, cx, cy)
        val text = getProgress().toInt().toString()
        if (!TextUtils.isEmpty(text)) {
            textPaint.color = textColor
            textPaint.textSize = textSize
            val textHeight = textPaint.descent() + textPaint.ascent()
            val textBaseline = (height - textHeight) / 2.0f
            canvas.drawText(
                text,
                (width - textPaint.measureText(text)) / 2.0f,
                textBaseline,
                textPaint
            )
            textPaint.textSize = suffixTextSize
            val suffixHeight = textPaint.descent() + textPaint.ascent()
            canvas.drawText(
                suffixText!!,
                width / 2.0f + textPaint.measureText(text) + suffixTextPadding,
                textBaseline + textHeight - suffixHeight,
                textPaint
            )
        }
        if (arcBottomHeight == 0f) {
            val radius = width / 2f
            val angle = (360 - arcAngle) / 2f
            arcBottomHeight =
                radius * (1 - Math.cos(angle / 180 * Math.PI)).toFloat()
        }
        if (!TextUtils.isEmpty(getBottomText())) {
            textPaint.textSize = bottomTextSize
            val bottomTextBaseline =
                height - arcBottomHeight - (textPaint.descent() + textPaint.ascent()) / 2
            canvas.drawText(
                getBottomText()!!,
                (width - textPaint.measureText(getBottomText())) / 2.0f,
                bottomTextBaseline,
                textPaint
            )
        }
    }

    override fun onSaveInstanceState(): Parcelable {
        val bundle = Bundle()
        bundle.putParcelable(INSTANCE_STATE, super.onSaveInstanceState())
        bundle.putFloat(INSTANCE_STROKE_WIDTH, getStrokeWidth())
        bundle.putFloat(INSTANCE_SUFFIX_TEXT_SIZE, getSuffixTextSize())
        bundle.putFloat(INSTANCE_SUFFIX_TEXT_PADDING, getSuffixTextPadding())
        bundle.putFloat(INSTANCE_BOTTOM_TEXT_SIZE, getBottomTextSize())
        bundle.putString(INSTANCE_BOTTOM_TEXT, getBottomText())
        bundle.putFloat(INSTANCE_TEXT_SIZE, getTextSize())
        bundle.putInt(INSTANCE_TEXT_COLOR, getTextColor())
        bundle.putFloat(INSTANCE_PROGRESS, getProgress())
        bundle.putInt(INSTANCE_MAX, max)
        bundle.putInt(
            INSTANCE_FINISHED_STROKE_START_COLOR,
            getFinishedStrokeStartColor()
        )
        bundle.putInt(
            INSTANCE_FINISHED_STROKE_END_COLOR,
            getFinishedStrokeEndColor()
        )
        bundle.putInt(
            INSTANCE_UNFINISHED_STROKE_COLOR,
            getUnfinishedStrokeColor()
        )
        bundle.putFloat(INSTANCE_ARC_ANGLE, getArcAngle())
        bundle.putString(INSTANCE_SUFFIX, getSuffixText())
        return bundle
    }

    override fun onRestoreInstanceState(state: Parcelable) {
        if (state is Bundle) {
            strokeWidth = state.getFloat(INSTANCE_STROKE_WIDTH)
            suffixTextSize = state.getFloat(INSTANCE_SUFFIX_TEXT_SIZE)
            suffixTextPadding = state.getFloat(INSTANCE_SUFFIX_TEXT_PADDING)
            bottomTextSize = state.getFloat(INSTANCE_BOTTOM_TEXT_SIZE)
            bottomText = state.getString(INSTANCE_BOTTOM_TEXT)
            textSize = state.getFloat(INSTANCE_TEXT_SIZE)
            textColor = state.getInt(INSTANCE_TEXT_COLOR)
            max = state.getInt(INSTANCE_MAX)
            setProgress(state.getFloat(INSTANCE_PROGRESS))
            finishedStrokeStartColor = state.getInt(INSTANCE_FINISHED_STROKE_START_COLOR)
            finishedStrokeEndColor = state.getInt(INSTANCE_FINISHED_STROKE_END_COLOR)
            unfinishedStrokeColor = state.getInt(INSTANCE_UNFINISHED_STROKE_COLOR)
            suffixText = state.getString(INSTANCE_SUFFIX)
            initPainters()
            super.onRestoreInstanceState(state.getParcelable(INSTANCE_STATE))
            return
        }
        super.onRestoreInstanceState(state)
    }
}
