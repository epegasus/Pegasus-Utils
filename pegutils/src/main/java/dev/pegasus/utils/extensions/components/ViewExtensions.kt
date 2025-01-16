package dev.pegasus.utils.extensions.components

import android.util.TypedValue
import android.view.View

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