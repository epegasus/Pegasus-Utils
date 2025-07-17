package dev.pegasus.utils.extensions.ui

import android.os.Bundle
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.NavDirections
import androidx.navigation.fragment.findNavController
import dev.pegasus.utils.utils.PegasusHelperUtils.withDelay

/**
 * @Author: SOHAIB AHMED
 * @Date: 02,April,2023
 * @Accounts
 *      -> https://github.com/epegasus
 *      -> https://stackoverflow.com/users/20440272/sohaib-ahmed
 */


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

/* ----------------------------------------- Keyboard -----------------------------------------*/

fun Fragment?.hideKeyboard() {
    val activity = this?.activity ?: return
    val view = this.view ?: activity.currentFocus ?: View(activity)
    val imm = ContextCompat.getSystemService(activity, InputMethodManager::class.java)
    imm?.hideSoftInputFromWindow(view.windowToken, 0)
}

fun Fragment?.showKeyboard(targetView: View? = this?.view) {
    val activity = this?.activity ?: return
    val view = targetView ?: return
    view.requestFocus()
    val imm = ContextCompat.getSystemService(activity, InputMethodManager::class.java)
    imm?.showSoftInput(view, InputMethodManager.SHOW_IMPLICIT)
}