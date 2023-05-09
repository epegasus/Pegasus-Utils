package dev.pegasus.utils.extensions.ui

import android.app.Activity
import android.content.Context
import android.widget.Toast
import dev.pegasus.utils.BuildConfig
import com.google.android.material.snackbar.Snackbar


/**
 * @Author: SOHAIB AHMED
 * @Date: 07,April,2023.
 * @Accounts
 *      -> https://github.com/epegasus
 *      -> https://www.linkedin.com/in/epegasus
 */


fun Context.getResString(stringId: Int): String {
    return resources.getString(stringId)
}

fun Context.showToast(message: String) {
    (this as Activity).runOnUiThread {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }
}

fun Context.showToast(stringId: Int) {
    val message = getResString(stringId)
    showToast(message)
}

fun Context.debugToast(message: String) {
    if (BuildConfig.DEBUG) {
        showToast(message)
    }
}

/* ---------- Snackbar ---------- */

fun Context.showSnackBar(message: String) {
    (this as Activity).runOnUiThread {
        Snackbar.make(findViewById(android.R.id.content), message, Snackbar.LENGTH_SHORT).show()
    }
}