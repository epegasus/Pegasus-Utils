package dev.pegasus.utils.extensions.ui

import android.app.Activity
import android.content.Context
import android.os.Build
import android.view.View
import android.view.WindowInsets
import android.view.WindowManager
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.annotation.StringRes
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.WindowInsetsControllerCompat
import androidx.fragment.app.FragmentContainerView

/**
 * @Author: SOHAIB AHMED
 * @Date: 20,March,2023
 * @Accounts
 *      -> https://github.com/epegasus
 *      -> https://stackoverflow.com/users/20440272/sohaib-ahmed
 */

fun Activity?.getResString(@StringRes stringResId: Int): String {
    this ?: return ""
    return getString(stringResId)
}

fun Activity?.showToast(message: String) {
    this ?: return
    runOnUiThread {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }
}

fun Activity?.showToast(@StringRes stringResId: Int) {
    showToast(getResString(stringResId))
}

/* ---------------------------------------------- BackPress ---------------------------------------------- */

fun AppCompatActivity.onBackPressedDispatcher(callback: () -> Unit) {
    onBackPressedDispatcher.addCallback(this, object : OnBackPressedCallback(true) {
        override fun handleOnBackPressed() {
            callback.invoke()
        }
    })
}

/* ---------------------------------------------- System UI ---------------------------------------------- */

fun Activity?.hideSystemUI(fcvContainerMain: FragmentContainerView) {
    this ?: return
    WindowCompat.setDecorFitsSystemWindows(window, false)
    WindowInsetsControllerCompat(window, fcvContainerMain).let { controller ->
        controller.hide(WindowInsetsCompat.Type.systemBars())
        controller.systemBarsBehavior = WindowInsetsControllerCompat.BEHAVIOR_SHOW_TRANSIENT_BARS_BY_SWIPE
    }
}

@Suppress("DEPRECATION")
fun Activity?.showSystemUI() {
    this ?: return
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
        WindowCompat.setDecorFitsSystemWindows(window, true)
        val controller = window.insetsController
        controller?.show(WindowInsets.Type.systemBars())
    } else {
        window.decorView.systemUiVisibility = (View.SYSTEM_UI_FLAG_VISIBLE)
        window.clearFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN)
    }
}

/* ---------------------------------------------- Keyboard ---------------------------------------------- */

// Hides the keyboard from the currently focused view
fun Activity?.hideKeyboard() {
    this ?: return
    val view = currentFocus ?: View(this)
    val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
    imm.hideSoftInputFromWindow(view.windowToken, 0)
}

// Shows the keyboard and focuses on the given view
fun Activity?.showKeyboard(view: View) {
    this ?: return
    view.requestFocus()
    val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
    imm.showSoftInput(view, InputMethodManager.SHOW_IMPLICIT)
}