package dev.pegasus.utils.adapters

import android.view.View
import androidx.databinding.BindingAdapter
import dev.pegasus.utils.listener.DebounceListener.setDebounceClickListener

/**
 * @Author: SOHAIB AHMED
 * @Date: 01,June,2023.
 * @Accounts
 *      -> https://github.com/epegasus
 *      -> https://www.linkedin.com/in/epegasus
 */

@BindingAdapter(value = ["debounceClick"])
fun setDebouncedClick(view: View, debounceClick: () -> Unit) {
    view.setDebounceClickListener {
        debounceClick.invoke()
    }
}