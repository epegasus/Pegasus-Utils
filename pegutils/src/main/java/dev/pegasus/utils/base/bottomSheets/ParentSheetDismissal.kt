package dev.pegasus.utils.base.bottomSheets

import android.content.DialogInterface
import android.util.Log
import androidx.fragment.app.FragmentManager
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

/**
 * @Author: SOHAIB AHMED
 * @Date: 25/04/2024
 * @Accounts
 *      -> https://github.com/epegasus
 *      -> https://stackoverflow.com/users/20440272/sohaib-ahmed
 */

open class ParentSheetDismissal : BottomSheetDialogFragment() {

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
            Log.e("ParentSheetDismissal", "showSafely: ", ex)
        }
    }

    fun safeDismiss(allowStateLoss: Boolean = true) {
        try {
            if (!isAdded || isRemoving || dialog == null) return
            if (allowStateLoss) dismissAllowingStateLoss()
            else dismiss()
        } catch (ex: IllegalStateException) {
            Log.e("ParentSheetDismissal", "safeDismiss: ", ex)
        }
    }
}