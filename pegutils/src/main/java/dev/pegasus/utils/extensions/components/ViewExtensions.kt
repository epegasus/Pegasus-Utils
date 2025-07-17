package dev.pegasus.utils.extensions.components

import android.graphics.Bitmap
import android.graphics.Canvas
import android.util.TypedValue
import android.view.View
import android.view.ViewGroup
import android.view.animation.AccelerateDecelerateInterpolator
import android.view.animation.Interpolator
import androidx.core.view.marginBottom
import androidx.core.view.marginLeft
import androidx.core.view.marginRight
import androidx.core.view.marginTop
import androidx.core.graphics.createBitmap

/**
 * @Author: SOHAIB AHMED
 * @Date: 20,March,2023
 * @Accounts
 *      -> https://github.com/epegasus
 *      -> https://stackoverflow.com/users/20440272/sohaib-ahmed
 */

fun View.gone() = run { visibility = View.GONE }

fun View.visible() = run { visibility = View.VISIBLE }

fun View.invisible() = run { visibility = View.INVISIBLE }

infix fun View.visibleIf(condition: Boolean) =
    run { visibility = if (condition) View.VISIBLE else View.GONE }

infix fun View.goneIf(condition: Boolean) =
    run { visibility = if (condition) View.GONE else View.VISIBLE }

infix fun View.invisibleIf(condition: Boolean) =
    run { visibility = if (condition) View.INVISIBLE else View.VISIBLE }

fun View.getCornerRadiusInPixel(radius: Float): Int {
    // Converting Dp to appropriate pixel for bitmap
    val temp = if (this.width > this.height) this.width else this.height
    val cornerRadiusDP = (radius * (temp.toFloat() / 500))
    return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, cornerRadiusDP, resources.displayMetrics).toInt()
}


fun View.getBitmap(): Bitmap? {
    try {
        val bitmap = createBitmap(width, height)
        val canvas = Canvas(bitmap)
        draw(canvas)
        return bitmap
    } catch (ex: Exception) {
        return null
    }
}

fun View.setMargins(left: Int = this.marginLeft, top: Int = this.marginTop, right: Int = this.marginRight, bottom: Int = this.marginBottom) {
    layoutParams = (layoutParams as ViewGroup.MarginLayoutParams).apply {
        setMargins(left, top, right, bottom)
    }
}

/**
 * Smoothly animates a content change in a [View] by fading out the current content,
 * performing the provided [updateAction], then fading the view back in.
 *
 * @param updateAction The action to perform while the view is faded out (e.g., update text/image).
 * @param fadeDuration Duration for both fade-out and fade-in animations in milliseconds. Default is 600ms.
 * @param interpolator The [Interpolator] used for both fade animations. Default is [AccelerateDecelerateInterpolator].
 * @param onAnimationEnd Optional callback invoked after the fade-in animation completes.
 *
 * @sample
 * myTextView.animateContentChange({
 *     myTextView.text = "New content"
 * })
 */

fun View.animateContentChange(
    updateAction: () -> Unit,
    fadeDuration: Long = 600L,
    interpolator: Interpolator = AccelerateDecelerateInterpolator(),
    onAnimationEnd: (() -> Unit)? = null
) {
    // Fade out
    this.animate()
        .alpha(0f)
        .setDuration(fadeDuration)
        .setInterpolator(interpolator)
        .withEndAction {
            updateAction() // Update content after fade-out

            // Fade in
            this.animate()
                .alpha(1f)
                .setDuration(fadeDuration)
                .setInterpolator(interpolator)
                .withEndAction {
                    onAnimationEnd?.invoke()
                }
                .start()
        }
        .start()
}