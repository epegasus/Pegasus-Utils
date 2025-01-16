package dev.pegasus.utils.dialogs

import android.content.DialogInterface
import androidx.fragment.app.DialogFragment

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
}