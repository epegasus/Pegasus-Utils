package dev.pegasus.utils.utils

import android.content.Context
import android.hardware.display.DisplayManager
import android.util.DisplayMetrics
import android.view.Display
import androidx.core.content.getSystemService
import dev.pegasus.utils.extensions.tools.printToErrorLog


/**
 * @Author: SOHAIB AHMED
 * @Date: 07,April,2023.
 * @Accounts
 *      -> https://github.com/epegasus
 *      -> https://www.linkedin.com/in/epegasus
 */


object PegasusScreenUtils {

    private const val DEFAULT_SCREEN_WIDTH = 350
    private const val DEFAULT_SCREEN_HEIGHT = 700

    /**
     * Returns the screen width in pixels.
     * Falls back to [DEFAULT_SCREEN_WIDTH] if not available.
     */
    fun Context?.getScreenWidth(defaultWidth: Int = DEFAULT_SCREEN_WIDTH): Int {
        return this?.getDisplayMetrics()?.widthPixels ?: defaultWidth
    }

    /**
     * Returns the screen height in pixels.
     * Falls back to [DEFAULT_SCREEN_HEIGHT] if not available.
     */
    fun Context?.getScreenHeight(defaultHeight: Int = DEFAULT_SCREEN_HEIGHT): Int {
        return this?.getDisplayMetrics()?.heightPixels ?: defaultHeight
    }

    /**
     * Attempts to retrieve accurate display metrics using DisplayManager.
     */
    private fun Context.getDisplayMetrics(): DisplayMetrics? {
        return try {
            val displayManager = getSystemService<DisplayManager>()
            val display = displayManager?.getDisplay(Display.DEFAULT_DISPLAY)
            val displayContext = display?.let { createDisplayContext(it) }
            displayContext?.resources?.displayMetrics
        } catch (ex: Exception) {
            ex.printToErrorLog("getDisplayMetrics")
            null
        }
    }
}