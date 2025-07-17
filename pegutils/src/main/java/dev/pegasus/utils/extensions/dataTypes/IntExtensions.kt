package dev.pegasus.utils.extensions.dataTypes

import android.content.res.Resources
import android.util.TypedValue
import androidx.recyclerview.widget.RecyclerView

/**
 * @Author: SOHAIB AHMED
 * @Date: 25,May,2023.
 * @Accounts
 *      -> https://github.com/epegasus
 *      -> https://www.linkedin.com/in/epegasus
 */

fun Int.isValidPosition(list: List<Any>): Boolean {
    return this != RecyclerView.NO_POSITION && list.isNotEmpty() && this < list.size
}


/**
 * Converts an [Int] value in dp (density-independent pixels) to px (pixels).
 *
 * @return The corresponding value in pixels.
 *
 * @sample
 * val paddingInPx = 16.toPx  // Converts 16dp to pixels
 */
val Int.toPx: Int
    get() = TypedValue.applyDimension(
        TypedValue.COMPLEX_UNIT_DIP,
        this.toFloat(),
        Resources.getSystem().displayMetrics
    ).toInt()

/**
 * Converts an [Int] value in px (pixels) to dp (density-independent pixels).
 *
 * @return The corresponding value in dp.
 *
 * @sample
 * val paddingInDp = 48.toDp  // Converts 48px to dp
 */
val Int.toDp: Int
    get() = (this / Resources.getSystem().displayMetrics.density).toInt()