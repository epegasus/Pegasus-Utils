package dev.pegasus.utils.utils

import android.content.Context
import android.os.Handler
import android.os.Looper
import androidx.annotation.StringRes


/**
 * @Author: SOHAIB AHMED
 * @Date: 07,April,2023.
 * @Accounts
 *      -> https://github.com/epegasus
 *      -> https://www.linkedin.com/in/epegasus
 */

object PegasusHelperUtils {

    const val TAG = "MyTag"

    fun withDelay(delay: Long = 300, block: () -> Unit) {
        Handler(Looper.getMainLooper()).postDelayed(block, delay)
    }

    fun getResString(context: Context, @StringRes stringId: Int): String {
        return context.resources.getString(stringId)
    }
}