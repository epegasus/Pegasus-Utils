package dev.pegasus.utils.utils

import kotlinx.coroutines.Dispatchers
import kotlin.coroutines.ContinuationInterceptor
import kotlin.coroutines.coroutineContext

/**
 * @Author: SOHAIB AHMED
 * @Date: 26,May,2023
 * @Accounts
 *      -> https://github.com/epegasus
 *      -> https://stackoverflow.com/users/20440272/sohaib-ahmed
 */

object PegasusCoroutineUtils {

    suspend fun checkCoroutineCurrentDispatcher(): String {
        return when (coroutineContext[ContinuationInterceptor]) {
            Dispatchers.Main -> "Dispatcher: Main"
            Dispatchers.IO -> "Dispatcher: IO"
            Dispatchers.Default -> "Dispatcher: Default"
            Dispatchers.Unconfined -> "Dispatcher: UnConfined"
            else -> "Dispatcher: No Idea"
        }
    }
}