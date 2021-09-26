package prava.arka.com.ui.util

import android.app.Activity
import android.content.res.ColorStateList
import android.graphics.Bitmap
import android.graphics.LinearGradient
import android.graphics.Outline
import android.graphics.Shader
import android.graphics.Typeface
import android.graphics.drawable.Drawable
import android.net.Uri
import android.os.Build
import android.text.TextPaint
import android.util.TypedValue
import android.view.View
import android.view.ViewGroup
import android.view.ViewOutlineProvider
import android.widget.Button
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import androidx.annotation.ColorInt
import androidx.annotation.ColorRes
import androidx.annotation.DimenRes
import androidx.annotation.DrawableRes
import androidx.annotation.FontRes
import androidx.annotation.StringRes
import androidx.appcompat.widget.AppCompatTextView
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.content.res.ResourcesCompat
import androidx.core.view.updatePadding
import androidx.core.widget.ImageViewCompat
import androidx.databinding.BindingAdapter
import androidx.viewpager2.widget.ViewPager2
import com.squareup.picasso.Callback
import com.squareup.picasso.NetworkPolicy
import com.squareup.picasso.Picasso
import kotlin.math.roundToInt

@BindingAdapter("imgUrl", "placeholder", "callback", requireAll = false)
fun ImageView.setImgUrl(
    imgUrl: String?,
    @DrawableRes placeHolder: Int?,
    callback: ImageLoaderCallback? = null
) {
    Picasso.get()
        .load(getSizeAppendedUrl(imgUrl))
        .also {
            if (placeHolder != null) {
                it.placeholder(placeHolder)
            }
        }
        .fit()
        .centerCrop() // todo fix these approximations
        .into(this, callback)
}

@BindingAdapter("imgBmp")
fun ImageView.setImgBmp(
    bmp: Bitmap,
) {
    setImageBitmap(bmp)
}

@BindingAdapter("imgBmpSafe")
fun ImageView.setImgBmpSafe(
    bmp: Bitmap?,
) {
    bmp?.let {
        setImageBitmap(it)
    }
}

@BindingAdapter("imgUri", "placeholder", requireAll = false)
fun ImageView.setImgUri(imgUri: Uri?, @DrawableRes placeHolder: Int?) {
    Picasso.get()
        .load(imgUri)
        .also {
            if (placeHolder != null) {
                it.placeholder(placeHolder)
            }
        }
        .fit()
        .centerCrop() // todo fix these approximations
        .into(this)
}

@BindingAdapter("imgContentRemote", "imgContentLocal", "placeholder", requireAll = false)
fun ImageView.setImgContent(
    imgContentRemote: String?,
    imgContentLocal: Uri?,
    @DrawableRes placeHolder: Int?
) {
    if (imgContentRemote != null) {
        setImgUrl(imgContentRemote, placeHolder, null)
    } else {
        setImgUri(imgContentLocal, placeHolder)
    }
}

@BindingAdapter("imgUrlOffline", "placeholder", requireAll = false)
fun ImageView.setImgUrlOffline(imgUrl: String?, @DrawableRes placeHolder: Int?) {
    Picasso.get()
        .load(getSizeAppendedUrl(imgUrl))
        .networkPolicy(NetworkPolicy.OFFLINE)
        .also {
            if (placeHolder != null) {
                it.placeholder(placeHolder)
            }
        }
        .fit()
        .centerCrop() // todo fix these approximations
        .into(this)
}

@BindingAdapter("imgDrawable")
fun ImageView.setImgDrawable(drawable: Drawable) {
    setImageDrawable(drawable)
}

@BindingAdapter("imgUrlPostponeTransition", "placeholder", requireAll = false)
fun ImageView.setImgUrlPostponeTransition(imgUrl: String?, @DrawableRes placeHolder: Int?) {
    Picasso.get()
        .load(getSizeAppendedUrl(imgUrl))
        .noFade()
        .networkPolicy(NetworkPolicy.OFFLINE)
        .also {
            if (placeHolder != null) {
                it.placeholder(placeHolder)
            }
        }
        .fit()
        .centerCrop() // todo fix these approximations
        .into(
            this,
            object : Callback {
                override fun onSuccess() {
                    ActivityCompat.startPostponedEnterTransition(this@setImgUrlPostponeTransition.context as Activity)
                }

                override fun onError(e: Exception?) {
                    ActivityCompat.startPostponedEnterTransition(this@setImgUrlPostponeTransition.context as Activity)
                }
            }
        )
}

@BindingAdapter("imgIconOrUrl", "url")
fun ImageView.setImgIconOrUrl(@DrawableRes icon: Int?, url: String?) {
    icon?.let {
        setImageResource(it)
    } ?: setImgUrl(url, null)
}

@BindingAdapter("backgroundDrawableRes")
fun View.setBackgroundDrawableRes(@DrawableRes backgroundDrawableRes: Int) {
    setBackgroundResource(backgroundDrawableRes)
}

@BindingAdapter("foregroundDrawableRes")
fun View.setForegroundDrawableRes(@DrawableRes drawableRes: Int) {
    if (Build.VERSION.SDK_INT >= 23) {
        val d = ContextCompat.getDrawable(context, drawableRes)
        foreground = d
    }
}

@BindingAdapter("imgSrcRes")
fun ImageView.setImgSrcRes(@DrawableRes imgSrc: Int) {
    setImageResource(imgSrc)
}

@BindingAdapter("imgTintRes")
fun ImageView.setImgTintRes(@ColorRes colorRes: Int) {
    val tintList = if (colorRes == 0)
        null
    else
        ColorStateList.valueOf(ContextCompat.getColor(context, colorRes))
    ImageViewCompat.setImageTintList(this, tintList)
}

@BindingAdapter("textColorRes")
fun TextView.setTextColorRes(@ColorRes textColor: Int) {
    if (textColor != 0) {
        setTextColor(ContextCompat.getColor(context, textColor))
    }
}

@BindingAdapter("textRes")
fun TextView.setTextRes(@StringRes textRes: Int) {
    if (textRes != 0) {
        setText(textRes)
    }
}

@BindingAdapter("roundedCorners")
fun View.setRoundedCorners(cornerRadius: Float) {
    this.outlineProvider = object : ViewOutlineProvider() {
        override fun getOutline(view: View?, outline: Outline?) {
            outline?.setRoundRect(
                0,
                0,
                view?.width ?: 0,
                view?.height ?: 0,
                cornerRadius
            )
        }
    }
    this.clipToOutline = true
}

@BindingAdapter("topRoundedCorners")
fun View.setTopRoundedCorners(cornerRadius: Float) {
    this.outlineProvider = object : ViewOutlineProvider() {
        override fun getOutline(view: View?, outline: Outline?) {
            outline?.setRoundRect(
                0,
                0,
                view?.width ?: 0,
                ((view?.height ?: 0) + cornerRadius.roundToInt()),
                cornerRadius
            )
        }
    }
    this.clipToOutline = true
}

@BindingAdapter("compoundDrawableLeftWithIntrinsicBounds")
fun TextView.setCompoundDrawableLeftWithIntrinsicBounds(@DrawableRes leftDrawableRes: Int) {
    this.setCompoundDrawablesWithIntrinsicBounds(leftDrawableRes, 0, 0, 0)
}

@BindingAdapter("imgSrc")
fun ImageView.setImgSrc(@DrawableRes drawableRes: Int) {
    this.setImageResource(drawableRes)
}

@BindingAdapter("imgTint")
fun ImageView.setImgTint(@ColorInt tint: Int) {
    setColorFilter(tint)
}

@BindingAdapter("buttonActivated")
fun Button.setButtonActivated(activated: Boolean) {
    this.isActivated = activated
}

// @BindingAdapter("animationRawRes")
// fun LottieAnimationView.setAnimationRawRes(@RawRes rawRes: Int?) {
//    if (rawRes != null) {
//        this.setAnimation(rawRes)
//        this.post {
//            this.playAnimation()
//        }
//    }
// }

@BindingAdapter("android:layout_marginBottom")
fun setBottomMargin(view: View, bottomMargin: Float) {
    val layoutParams: ViewGroup.MarginLayoutParams =
        view.layoutParams as ViewGroup.MarginLayoutParams
    layoutParams.setMargins(
        layoutParams.leftMargin,
        layoutParams.topMargin,
        layoutParams.rightMargin,
        bottomMargin.roundToInt()
    )
    view.layoutParams = layoutParams
}

@BindingAdapter("android:layout_marginTop")
fun setTopMargin(view: View, topMargin: Float) {
    val layoutParams: ViewGroup.MarginLayoutParams =
        view.layoutParams as ViewGroup.MarginLayoutParams
    layoutParams.setMargins(
        layoutParams.leftMargin,
        topMargin.roundToInt(),
        layoutParams.rightMargin,
        layoutParams.bottomMargin
    )
    view.layoutParams = layoutParams
}

@BindingAdapter("progressDrawable")
fun ProgressBar.setProgressBarDrawable(drawable: Drawable) {
    progressDrawable = drawable
}

@BindingAdapter("gradientText", "gradientStartColor", "gradientEndColor")
fun TextView.setTextWithGradient(text: String?, @ColorInt startColor: Int, @ColorInt endColor: Int) {
    text ?: return
    val paint: TextPaint = paint
    val width: Float = paint.measureText(text)

    val textShader: Shader = LinearGradient(
        0f,
        0f,
        width,
        textSize,
        intArrayOf(
            startColor,
            endColor
        ),
        null,
        Shader.TileMode.CLAMP
    )
    paint.shader = textShader
    this.text = text
}

@BindingAdapter("gradientText", "gradientStartColor", "gradientEndColor")
fun TextView.setTextWithGradient(
    @StringRes stringResource: Int?,
    @ColorInt startColor: Int,
    @ColorInt endColor: Int
) {
    stringResource ?: return
    val text = this.context.getText(stringResource).toString()
    val paint: TextPaint = paint
    val width: Float = paint.measureText(text)

    val textShader: Shader = LinearGradient(
        0f,
        0f,
        width,
        textSize,
        intArrayOf(
            startColor,
            endColor
        ),
        null,
        Shader.TileMode.CLAMP
    )
    paint.shader = textShader
    this.text = text
}

@BindingAdapter("viewHeight")
fun View.setViewHeight(viewHeight: Int) {
    setHeight(viewHeight)
}

@BindingAdapter("viewWidth")
fun View.setViewWidth(viewWidth: Int) {
    setWidth(viewWidth)
}

@BindingAdapter("viewEnabled")
fun View.setViewEnabled(enabled: Boolean) {
    isEnabled = enabled
}

@BindingAdapter("pagerCurrentItem", "smoothScroll", requireAll = false)
fun ViewPager2.setPagerCurrentItem(currentItem: Int, smoothScroll: Boolean = false) {
    setCurrentItem(currentItem, smoothScroll)
}

@BindingAdapter("paddingLeft")
fun View.setPaddingLeft(@DimenRes dimen: Int) {
    updatePadding(left = resources.getDimensionPixelOffset(dimen))
}

@BindingAdapter("paddingRight")
fun View.setPaddingRight(@DimenRes dimen: Int) {
    updatePadding(right = resources.getDimensionPixelOffset(dimen))
}

@BindingAdapter("paddingTop")
fun View.setPaddingTop(@DimenRes dimen: Int) {
    updatePadding(top = resources.getDimensionPixelOffset(dimen))
}

@BindingAdapter("paddingBottom")
fun View.setPaddingBottom(@DimenRes dimen: Int) {
    updatePadding(bottom = resources.getDimensionPixelOffset(dimen))
}

@BindingAdapter("textViewFont")
fun AppCompatTextView.setTextViewStyle(@FontRes fontRes: Int) {
    val typeface: Typeface = ResourcesCompat.getFont(context, fontRes)!!
    setTypeface(typeface)
}

@BindingAdapter("textSizeRes")
fun AppCompatTextView.setTextViewTextSize(@DimenRes size: Int) {
    val s = resources.getDimension(size) / resources.displayMetrics.density
    setTextSize(TypedValue.COMPLEX_UNIT_DIP, s)
}
