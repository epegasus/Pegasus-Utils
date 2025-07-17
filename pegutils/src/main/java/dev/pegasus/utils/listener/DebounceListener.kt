package dev.pegasus.utils.listener

import android.os.SystemClock
import android.view.MenuItem
import android.view.View
import androidx.appcompat.widget.Toolbar
import com.google.android.material.appbar.MaterialToolbar
import java.util.WeakHashMap

/**
 * @Author: SOHAIB AHMED
 * @Date: 07,April,2023.
 * @Accounts
 *      -> https://github.com/epegasus
 *      -> https://www.linkedin.com/in/epegasus
 */

object DebounceListener {

    private const val DEBOUNCE_DEFAULT_TIME = 500L
    private val lastClickMap = WeakHashMap<View, Long>()

    fun View.setDebounceClickListener(debounceTime: Long = DEBOUNCE_DEFAULT_TIME, action: () -> Unit) {
        setOnClickListener {
            val currentTime = SystemClock.elapsedRealtime()
            val lastClickTime = lastClickMap[this] ?: 0L
            if (currentTime - lastClickTime >= debounceTime) {
                lastClickMap[this] = currentTime
                action()
            }
        }
    }

    fun MaterialToolbar.setDebounceNavigationClickListener(debounceTime: Long = DEBOUNCE_DEFAULT_TIME, action: () -> Unit) {
        setNavigationOnClickListener {
            val currentTime = SystemClock.elapsedRealtime()
            val lastClickTime = lastClickMap[this] ?: 0L
            if (currentTime - lastClickTime >= debounceTime) {
                lastClickMap[this] = currentTime
                action()
            }
        }
    }

    fun MaterialToolbar.setDebounceMenuItemClickListener(debounceTime: Long = DEBOUNCE_DEFAULT_TIME, action: (item: MenuItem) -> Unit) {
        setOnMenuItemClickListener { item ->
            val currentTime = SystemClock.elapsedRealtime()
            val lastClickTime = lastClickMap[this] ?: 0L
            return@setOnMenuItemClickListener if (currentTime - lastClickTime >= debounceTime) {
                lastClickMap[this] = currentTime
                action(item)
                true
            } else {
                false
            }
        }
    }
}