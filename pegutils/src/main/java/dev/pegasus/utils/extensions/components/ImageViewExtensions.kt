package dev.pegasus.utils.extensions.components

import android.graphics.PorterDuff
import androidx.annotation.ColorRes
import androidx.core.content.ContextCompat
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