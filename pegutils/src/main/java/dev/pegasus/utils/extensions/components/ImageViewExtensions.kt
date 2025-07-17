@file:Suppress("unused")

package dev.pegasus.utils.extensions.components

import android.graphics.Bitmap
import android.graphics.PorterDuff
import android.graphics.drawable.Drawable
import android.net.Uri
import android.widget.ImageView
import androidx.annotation.ColorRes
import androidx.annotation.DrawableRes
import androidx.annotation.RawRes
import androidx.core.content.ContextCompat
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.bumptech.glide.request.RequestOptions
import com.bumptech.glide.request.transition.DrawableCrossFadeFactory
import com.google.android.material.imageview.ShapeableImageView

/**
 * @Author: SOHAIB AHMED
 * @Date: 22,March,2023
 * @Accounts
 *      -> https://github.com/epegasus
 *      -> https://stackoverflow.com/users/20440272/sohaib-ahmed
 */

/**
 * @param   colorId: e.g. (R.color.black)
 *          Works perfectly for vector assets
 */

fun ShapeableImageView.changeColor(@ColorRes colorId: Int) {
    this.setColorFilter(ContextCompat.getColor(context, colorId), PorterDuff.Mode.SRC_IN)
}


/**
 * Glide extension utilities for [ImageView].
 * Handles loading from various sources with optional crossfade transitions and basic configurations.
 */

/** Glide crossfade factory for smoother image transitions */
private val crossFadeFactory = DrawableCrossFadeFactory.Builder().setCrossFadeEnabled(true).build()

/**
 * Load image from drawable resource.
 * @param drawableRes Drawable resource ID.
 */
fun ImageView.loadDrawable(@DrawableRes drawableRes: Int) {
    Glide.with(this)
        .load(drawableRes)
        .transition(DrawableTransitionOptions.with(crossFadeFactory))
        .into(this)
}

/**
 * Load image from Uri.
 * @param uri Uri of the image.
 */
fun ImageView.loadUri(uri: Uri?) {
    Glide.with(this)
        .load(uri)
        .transition(DrawableTransitionOptions.with(crossFadeFactory))
        .into(this)
}

/**
 * Load image from a local or remote file path.
 * @param path The absolute or URL path of the image.
 */
fun ImageView.loadPath(path: String?) {
    Glide.with(this)
        .load(path)
        .transition(DrawableTransitionOptions.with(crossFadeFactory))
        .into(this)
}

/**
 * Load image from a [Bitmap].
 * @param bitmap Bitmap image.
 */
fun ImageView.loadBitmap(bitmap: Bitmap?) {
    Glide.with(this)
        .asBitmap()
        .load(bitmap)
        .into(this)
}

/**
 * Load image from a [Drawable] directly.
 * @param drawable Drawable object.
 */
fun ImageView.loadDrawable(drawable: Drawable?) {
    Glide.with(this)
        .load(drawable)
        .transition(DrawableTransitionOptions.with(crossFadeFactory))
        .into(this)
}

/**
 * Load a circular image from Uri or URL.
 * @param uri Image Uri or URL.
 */
fun ImageView.loadCircular(uri: Any?) {
    Glide.with(this)
        .load(uri)
        .apply(RequestOptions.circleCropTransform())
        .transition(DrawableTransitionOptions.with(crossFadeFactory))
        .into(this)
}

/**
 * Load image with optional placeholder and error drawable.
 * @param model The image model (can be URL, Uri, File, resource, etc.).
 * @param placeholder Drawable shown while loading.
 * @param error Drawable shown on load failure.
 */
fun ImageView.loadWithPlaceholders(model: Any?, @DrawableRes placeholder: Int? = null, @DrawableRes error: Int? = null) {
    val request = Glide.with(this).load(model).transition(DrawableTransitionOptions.with(crossFadeFactory))
    placeholder?.let { request.placeholder(it) }
    error?.let { request.error(it) }
    request.into(this)
}

/**
 * Load image with custom size override.
 * @param model The image model.
 * @param width Desired width.
 * @param height Desired height.
 */
fun ImageView.loadWithSize(model: Any?, width: Int, height: Int) {
    Glide.with(this)
        .load(model)
        .override(width, height)
        .transition(DrawableTransitionOptions.with(crossFadeFactory))
        .into(this)
}

/**
 * Clear any image loading request and cancel Glide task for this ImageView.
 */
fun ImageView.clearImage() {
    Glide.with(this).clear(this)
}

/**
 * Load image from raw resource (e.g., raw gif).
 */
fun ImageView.loadRawResource(@RawRes rawResId: Int) {
    Glide.with(this)
        .load(rawResId)
        .transition(DrawableTransitionOptions.with(crossFadeFactory))
        .diskCacheStrategy(DiskCacheStrategy.NONE)
        .into(this)
}