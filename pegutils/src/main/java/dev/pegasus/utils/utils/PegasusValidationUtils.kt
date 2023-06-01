package dev.pegasus.utils.utils

import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout

/**
 * @Author: SOHAIB AHMED
 * @Date: 28,April,2023.
 * @Accounts
 *      -> https://github.com/epegasus
 *      -> https://www.linkedin.com/in/epegasus
 */

object PegasusValidationUtils {

    fun isNotEmpty(layout: TextInputLayout, edittext: TextInputEditText, errorMessage: String? = null): Boolean {
        val text = edittext.text?.trim().toString()
        return if (text.isEmpty()) {
            layout.error = errorMessage ?: "Field can't be empty."
            layout.isErrorEnabled = true
            false
        } else {
            layout.isErrorEnabled = false
            layout.error = ""
            true
        }
    }
}