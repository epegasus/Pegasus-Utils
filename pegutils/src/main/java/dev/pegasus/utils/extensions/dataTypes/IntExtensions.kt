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

val Int.toPx: Int get() = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, this.toFloat(), Resources.getSystem().displayMetrics).toInt()