package dev.pegasus.utils.utils

import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import java.util.TimeZone

/**
 * @Author: SOHAIB AHMED
 * @Date: 13,February,2023
 * @Accounts
 *      -> https://github.com/epegasus
 *      -> https://stackoverflow.com/users/20440272/sohaib-ahmed
 */

object PegasusDateUtils {

    fun String.toDate(format: String = "yyyy-MM-dd HH:mm:ss"): Date? {
        val simpleDateFormat = SimpleDateFormat(format, Locale.getDefault())
        return simpleDateFormat.parse(this)
    }

    fun Date.toStringFormat(format: String = "yyyy-MM-dd HH:mm:ss"): String {
        val simpleDateFormat = SimpleDateFormat(format, Locale.getDefault())
        return simpleDateFormat.format(this)
    }

    fun getTimePassed(dateGmt: String): String {
        val utcTime = getUtcTime(dateGmt)
        val currentTime = getUtcTime(System.currentTimeMillis())

        val timePassedInSeconds = ((currentTime - utcTime) / 1000).toInt()

        val minutes = timePassedInSeconds / 60
        val hours = minutes / 60
        val days = hours / 24
        val weeks = days / 7
        val months = days / 30
        val years = days / 365

        return when {
            years > 0 -> "$years y"
            months > 0 -> "$months m"
            weeks > 0 -> "$weeks w"
            days > 0 -> "$days d"
            hours > 0 -> "$hours h"
            minutes > 0 -> "$minutes m"
            else -> "$timePassedInSeconds s"
        }
    }

    fun getTimePassed(timestamp: Long): String {
        val utcTime = getUtcTime(timestamp)
        val currentTime = getUtcTime(System.currentTimeMillis())

        val timePassedInSeconds = ((currentTime - utcTime) / 1000).toInt()

        val minutes = timePassedInSeconds / 60
        val hours = minutes / 60
        val days = hours / 24
        val weeks = days / 7
        val months = days / 30
        val years = days / 365

        return when {
            years > 0 -> "$years y"
            months > 0 -> "$months m"
            weeks > 0 -> "$weeks w"
            days > 0 -> "$days d"
            hours > 0 -> "$hours h"
            minutes > 0 -> "$minutes m"
            else -> "$timePassedInSeconds s"
        }
    }

    private fun getUtcTime(timestamp: Long): Long {
        val dateFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'", Locale.getDefault())
        dateFormat.timeZone = TimeZone.getTimeZone("UTC")

        val utcTimeString = dateFormat.format(Date(timestamp))
        return dateFormat.parse(utcTimeString)?.time ?: 0
    }

    private fun getUtcTime(dateGmt: String): Long {
        val dateFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss", Locale.getDefault())
        dateFormat.timeZone = TimeZone.getTimeZone("UTC")

        return dateFormat.parse(dateGmt)?.time ?: 0
    }
}