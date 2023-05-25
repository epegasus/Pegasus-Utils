package dev.pegasus.utils.extensions.dataTypes

import java.text.DecimalFormat
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import kotlin.math.log10
import kotlin.math.pow


/**
 * @Author: SOHAIB AHMED
 * @Date: 25,May,2023.
 * @Accounts
 *      -> https://github.com/epegasus
 *      -> https://www.linkedin.com/in/epegasus
 */

fun Long.getFormattedSize(): String {
    if (this <= 0) return "0"
    val units = arrayOf("B", "kB", "MB", "GB", "TB")
    val digitGroups = (log10(this.toDouble()) / log10(1024.0)).toInt()
    return DecimalFormat("#,##0.#").format(this / 1024.0.pow(digitGroups.toDouble())).toString() + " " + units[digitGroups]
}

fun Long.getFormattedDate(): String {
    if (this == 0L) return "Jan 01, 1997"
    val lastModDate = Date(this)
    val dateFormat = SimpleDateFormat("MMM dd, yyyy", Locale.getDefault())
    return dateFormat.format(lastModDate)
}