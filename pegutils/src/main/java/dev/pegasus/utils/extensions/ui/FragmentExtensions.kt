package dev.pegasus.utils.extensions.ui

import android.app.Activity
import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.content.Context.INPUT_METHOD_SERVICE
import android.os.Bundle
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.annotation.StringRes
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.withCreated
import androidx.lifecycle.withResumed
import androidx.lifecycle.withStarted
import androidx.navigation.NavController
import androidx.navigation.NavDirections
import androidx.navigation.fragment.findNavController
import com.google.android.material.snackbar.Snackbar
import dev.pegasus.utils.extensions.tools.printToDebugLog
import dev.pegasus.utils.utils.PegasusHelperUtils.withDelay
import kotlinx.coroutines.launch

/**
 * @Author: SOHAIB AHMED
 * @Date: 02,April,2023
 * @Accounts
 *      -> https://github.com/epegasus
 *      -> https://stackoverflow.com/users/20440272/sohaib-ahmed
 */

/* ----------------------------------------- Launch's -----------------------------------------*/

fun Fragment.launchWhenCreated(callback: () -> Unit) {
    lifecycleScope.launch { lifecycle.withCreated(callback) }
}

fun Fragment.launchWhenStarted(callback: () -> Unit) {
    lifecycleScope.launch { lifecycle.withStarted(callback) }
}

fun Fragment.launchWhenResumed(callback: () -> Unit) {
    lifecycleScope.launch { lifecycle.withResumed(callback) }
}

/* ----------------------------------------- Navigation's -----------------------------------------*/

/**
 *     Used launchWhenCreated, bcz of screen rotation
 * @param fragmentId : Current Fragment's Id (from Nav Graph)
 * @param action : Action / Id of other fragment
 * @param bundle : Pass bundle as a NavArgs to destination.
 */

fun Fragment.navigateTo(fragmentId: Int, action: Int, bundle: Bundle) {
    launchWhenCreated {
        if (isAdded && isCurrentDestination(fragmentId)) {
            findNavController().navigate(action, bundle)
        }
    }
}

fun Fragment.navigateTo(fragmentId: Int, action: Int) {
    launchWhenCreated {
        if (isAdded && isCurrentDestination(fragmentId)) {
            findNavController().navigate(action)
        }
    }
}

fun Fragment.navigateTo(fragmentId: Int, action: NavDirections) {
    launchWhenCreated {
        if (isAdded && isCurrentDestination(fragmentId)) {
            findNavController().navigate(action)
        }
    }
}

fun Fragment.popFrom(fragmentId: Int) {
    launchWhenCreated {
        if (isAdded && isCurrentDestination(fragmentId)) {
            findNavController().popBackStack()
        }
    }
}

fun Fragment.popFrom(fragmentId: Int, destinationFragmentId: Int, inclusive: Boolean = false) {
    launchWhenCreated {
        if (isAdded && isCurrentDestination(fragmentId)) {
            findNavController().popBackStack(destinationFragmentId, inclusive)
        }
    }
}

fun Fragment.isCurrentDestination(fragmentId: Int): Boolean {
    return findNavController().currentDestination?.id == fragmentId
}

/* --------------------------- With Parent NavController --------------------------- */

fun Fragment.navigateTo(navController: NavController, fragmentId: Int, action: Int, bundle: Bundle) {
    launchWhenCreated {
        if (isAdded && isCurrentDestination(navController, fragmentId)) {
            navController.navigate(action, bundle)
        }
    }
}

fun Fragment.navigateTo(navController: NavController, fragmentId: Int, action: Int) {
    launchWhenCreated {
        if (isAdded && isCurrentDestination(navController, fragmentId)) {
            navController.navigate(action)
        }
    }
}

fun Fragment.navigateTo(navController: NavController, fragmentId: Int, action: NavDirections) {
    launchWhenCreated {
        if (isAdded && isCurrentDestination(navController, fragmentId)) {
            navController.navigate(action)
        }
    }
}

fun Fragment.popFrom(navController: NavController, fragmentId: Int) {
    launchWhenCreated {
        if (isAdded && isCurrentDestination(navController, fragmentId)) {
            navController.popBackStack()
        }
    }
}

fun Fragment.isCurrentDestination(navController: NavController, fragmentId: Int): Boolean {
    return navController.currentDestination?.id == fragmentId
}

/* ----------------------------------------- Delays -----------------------------------------*/

fun Fragment.withDelaySafe(delay: Long = 300, block: () -> Unit) {
    withDelay(delay) {
        if (isAdded) block.invoke()
    }
}

fun Fragment.launchWhenResumeWithDelay(delay: Long = 300, block: () -> Unit) {
    withDelay(delay) {
        launchWhenResumed { block.invoke() }
    }
}

/* ----------------------------------------- General -----------------------------------------*/

fun Fragment.getResString(@StringRes stringResId: Int): String {
    return context?.getString(stringResId) ?: ""
}

fun Fragment.showToast(message: String) {
    activity?.let {
        it.runOnUiThread {
            Toast.makeText(it, message, Toast.LENGTH_SHORT).show()
        }
    }
}

fun Fragment.showToast(@StringRes stringResId: Int) {
    showToast(getResString(stringResId))
}

fun Fragment.showSnackbar(message: String, anchorView: View? = null, duration: Int = Snackbar.LENGTH_SHORT) {
    val v: View? = anchorView ?: view
    v?.let {
        val snackbar = Snackbar.make(it, message, duration)
        snackbar.anchorView = anchorView
        snackbar.show()
    }
}

fun Fragment.showSnackbar(@StringRes stringResId: Int, anchorView: View? = null, duration: Int = Snackbar.LENGTH_SHORT) {
    val v: View? = anchorView ?: view
    v?.let {
        val snackbar = Snackbar.make(it, getResString(stringResId), duration)
        snackbar.anchorView = anchorView
        snackbar.show()
    }
}

/**
 * @param view: View should be of edittext or something
 */
fun Fragment.showKeyboard(activity: Activity, view: View) {
    val imm = activity.getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
    imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, 0)
    withDelay(500) {
        view.requestFocus()
    }
}

fun Fragment.hideKeyboard() {
    activity?.apply {
        val imm: InputMethodManager = getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
        val view = currentFocus ?: View(this)
        imm.hideSoftInputFromWindow(view.windowToken, InputMethodManager.HIDE_NOT_ALWAYS)
    }
}

fun Fragment.copyClipboardData(label: String, data: String) {
    context?.let {
        try {
            val clipboard = it.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
            val clip: ClipData = ClipData.newPlainText(label, data)
            clipboard.setPrimaryClip(clip)
        } catch (e: Exception) {
            e.printToDebugLog("ToolsUtils: copyClipboardData: Exception")
        }
    }
}