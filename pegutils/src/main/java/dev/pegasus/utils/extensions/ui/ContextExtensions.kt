package dev.pegasus.utils.extensions.ui

import android.app.Activity
import android.content.Context
import android.graphics.drawable.Drawable
import android.view.View
import android.widget.Toast
import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.core.content.ContextCompat
import com.google.android.material.snackbar.Snackbar


/**
 * @Author: SOHAIB AHMED
 * @Date: 07,April,2023.
 * @Accounts
 *      -> https://github.com/epegasus
 *      -> https://www.linkedin.com/in/epegasus
 */


/* ---------------------------------------------- Resources ---------------------------------------------- */

fun Context?.getResString(@StringRes stringId: Int): String {
    return this?.getString(stringId).orEmpty()
}

fun Context?.getDrawableResource(@DrawableRes drawableId: Int): Drawable? {
    return this?.let { ContextCompat.getDrawable(it, drawableId) }
}

/* ---------------------------------------------- Toast ---------------------------------------------- */

fun Context?.showToast(message: String) {
    if (this == null) return
    Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
}

fun Context?.showToast(@StringRes stringId: Int) {
    this?.showToast(this.getResString(stringId))
}

/* ---------------------------------------------- SnackBar ---------------------------------------------- */


fun Context?.showSnackBar(message: String, anchorView: View? = null, duration: Int = Snackbar.LENGTH_SHORT) {
    val v: View? = anchorView ?: (this as? Activity)?.findViewById(android.R.id.content)
    v?.let {
        val snackBar = Snackbar.make(it, message, duration)
        snackBar.anchorView = anchorView
        snackBar.show()
    }
}

fun Context?.showSnackBar(@StringRes stringResId: Int, anchorView: View? = null, duration: Int = Snackbar.LENGTH_SHORT) {
    this?.showSnackBar(this.getResString(stringResId), anchorView, duration)
}