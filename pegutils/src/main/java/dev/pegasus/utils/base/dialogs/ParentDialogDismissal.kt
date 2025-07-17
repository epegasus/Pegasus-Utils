package dev.pegasus.utils.base.dialogs

import android.content.DialogInterface
import android.util.Log
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.FragmentManager

/**
 * @Author: SOHAIB AHMED
 * @Date: 17/01/2025
 * @Accounts
 *      -> https://github.com/epegasus
 *      -> https://www.linkedin.com/in/epegasus
 */

open class ParentDialogDismissal : DialogFragment() {

    var dismissCallback: (() -> Unit)? = null

    override fun onDismiss(dialog: DialogInterface) {
        super.onDismiss(dialog)
        dismissCallback?.invoke()
    }

    fun showSafely(fragmentManager: FragmentManager, tag: String = this::class.java.simpleName) {
        try {
            if (isAdded || fragmentManager.isStateSaved || fragmentManager.findFragmentByTag(tag) != null) return
            show(fragmentManager, tag)
        } catch (ex: IllegalStateException) {
            Log.e("ParentDialogDismissal", "showSafely: ", ex)
        }
    }

    fun safeDismiss(allowStateLoss: Boolean = true) {
        try {
            if (!isAdded || isRemoving || dialog == null) return
            if (allowStateLoss) dismissAllowingStateLoss()
            else dismiss()
        } catch (ex: IllegalStateException) {
            Log.e("ParentDialogDismissal", "safeDismiss: ", ex)
        }
    }
}