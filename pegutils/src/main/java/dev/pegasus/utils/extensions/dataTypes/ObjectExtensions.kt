package dev.pegasus.utils.extensions.dataTypes

import androidx.recyclerview.widget.RecyclerView

/**
 * @Author: SOHAIB AHMED
 * @Date: 20,March,2023
 * @Accounts
 *      -> https://github.com/epegasus
 *      -> https://stackoverflow.com/users/20440272/sohaib-ahmed
 */

val Any?.isNull get() = this == null

fun Int.isValidPosition(list: List<Any>): Boolean {
    return this != RecyclerView.NO_POSITION && list.isNotEmpty() && this < list.size
}