package dev.pegasus.utils.bottomSheets

import android.content.DialogInterface
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

/**
 * @Author: SOHAIB AHMED
 * @Date: 25/04/2024
 * @Accounts
 *      -> https://github.com/epegasus
 *      -> https://stackoverflow.com/users/20440272/sohaib-ahmed
 */

open class MyBottomSheetDialog : BottomSheetDialogFragment() {

    var dismissCallback: (() -> Unit)? = null

    override fun onDismiss(dialog: DialogInterface) {
        super.onDismiss(dialog)
        dismissCallback?.invoke()
    }
}