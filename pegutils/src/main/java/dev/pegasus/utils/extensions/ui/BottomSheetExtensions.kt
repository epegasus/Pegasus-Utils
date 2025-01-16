package dev.pegasus.utils.extensions.ui

import android.util.Log
import androidx.fragment.app.Fragment
import dev.pegasus.utils.bottomSheets.MyBottomSheetDialog
import dev.pegasus.utils.utils.PegasusHelperUtils.TAG

/**
 * @Author: SOHAIB AHMED
 * @Date: 25/04/2024
 * @Accounts
 *      -> https://github.com/epegasus
 *      -> https://stackoverflow.com/users/20440272/sohaib-ahmed
 */

/**
 * @param isBottomSheetShown:  Create a global variable in fragment to check if the bottom sheet is shown or not.
 */
fun MyBottomSheetDialog.showSafe(fragment: Fragment?, isBottomSheetShown: Boolean, callback: (Boolean) -> Unit) {
    if (this.isAdded || fragment == null || !fragment.isAdded || fragment.childFragmentManager.findFragmentByTag(tag) != null || isBottomSheetShown) {
        Log.e(TAG, "BottomSheet: showSafe: Not added")
        return
    }

    show(fragment.childFragmentManager, tag)
    this.dismissCallback = { callback.invoke(false) }
    callback.invoke(true)
}