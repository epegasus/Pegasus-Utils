package dev.pegasus.utils.utils

import android.content.Context
import android.hardware.display.DisplayManager
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

    private const val defaultScreenWidth = 350
    private const val defaultScreenHeight = 700

    fun Context?.getScreenWidth(): Int {
        this?.let {
            try {
                val defaultDisplay = it.getSystemService<DisplayManager>()?.getDisplay(Display.DEFAULT_DISPLAY)
                defaultDisplay?.let { display ->
                    val displayContext = it.createDisplayContext(display)
                    return displayContext.resources.displayMetrics.widthPixels
                }
            } catch (ex: Exception) {
                ex.printToErrorLog("getScreenWidth")
            }
        }
        return defaultScreenWidth
    }

    fun Context?.getScreenHeight(): Int {
        this?.let {
            try {
                val defaultDisplay = it.getSystemService<DisplayManager>()?.getDisplay(Display.DEFAULT_DISPLAY)
                defaultDisplay?.let { display ->
                    val displayContext = it.createDisplayContext(display)
                    return displayContext.resources.displayMetrics.heightPixels
                }
            } catch (ex: Exception) {
                ex.printToErrorLog("getScreenHeight")
            }
        }
        return defaultScreenHeight
    }
}