package dev.pegasus.utils.extensions.uiComponents

import android.graphics.PorterDuff
import android.graphics.PorterDuffColorFilter
import androidx.annotation.ColorRes
import androidx.core.content.ContextCompat
import com.google.android.material.textview.MaterialTextView

/**
 * @Author: SOHAIB AHMED
 * @Date: 22,March,2023
 * @Accounts
 *      -> https://github.com/epegasus
 *      -> https://stackoverflow.com/users/20440272/sohaib-ahmed
 */

/**
 * @param   colorId: e.g. (R.color.black)
 */

fun MaterialTextView.changeColor(@ColorRes colorId: Int) {
    val textColor = ContextCompat.getColor(this.context, colorId)
    setTextColor(textColor)

    val colorFilter = PorterDuffColorFilter(textColor, PorterDuff.Mode.SRC_IN)
    compoundDrawables.forEach { drawable ->
        drawable?.let {
            val temp = it.mutate()
            temp.colorFilter = colorFilter
            setCompoundDrawablesWithIntrinsicBounds(null, temp, null, null)
        }
    }
}