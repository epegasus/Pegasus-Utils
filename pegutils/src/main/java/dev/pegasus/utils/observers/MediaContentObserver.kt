package dev.pegasus.utils.observers

import android.content.ContentResolver
import android.database.ContentObserver
import android.net.Uri
import android.os.Handler
import android.os.Looper
import android.provider.MediaStore

/**
 * @Author: SOHAIB AHMED
 * @Date: 26,May,2023
 * @Accounts
 *      -> https://github.com/epegasus
 *      -> https://stackoverflow.com/users/20440272/sohaib-ahmed
 */

class MediaContentObserver(private val contentResolver: ContentResolver, private val onChangeCallback: () -> Unit) : ContentObserver(Handler(Looper.getMainLooper())) {

    private var lastTimeOfCall = 0L
    private var lastTimeOfUpdate = 0L
    var thresholdTime: Long = 5000

    override fun onChange(selfChange: Boolean, uri: Uri?) {
        super.onChange(selfChange, uri)

        // Call the provided callback when a change is detected

        lastTimeOfCall = System.currentTimeMillis()

        if (lastTimeOfCall - lastTimeOfUpdate > thresholdTime) {
            onChangeCallback.invoke()
            lastTimeOfUpdate = System.currentTimeMillis()
        }
    }

    fun register() {
        contentResolver.registerContentObserver(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, true, this)
    }

    fun unregister() {
        contentResolver.unregisterContentObserver(this)
    }
}