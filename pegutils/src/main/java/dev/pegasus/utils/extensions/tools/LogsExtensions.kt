package dev.pegasus.utils.extensions.tools

import android.util.Log
import dev.pegasus.utils.utils.PegasusHelperUtils.TAG

/**
 * @Author: SOHAIB AHMED
 * @Date: 20,March,2023
 * @Accounts
 *      -> https://github.com/epegasus
 *      -> https://stackoverflow.com/users/20440272/sohaib-ahmed
 */

fun Any?.printToDebugLog(tag: String = TAG) {
    Log.d(tag, toString())
}

fun Exception.printToErrorLog(message: Any, tag: String = TAG) {
    Log.e(tag, message.toString(), this)
}
