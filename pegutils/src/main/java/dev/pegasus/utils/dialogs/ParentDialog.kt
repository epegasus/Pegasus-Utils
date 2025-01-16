package dev.pegasus.utils.dialogs

import android.app.Activity
import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.viewbinding.ViewBinding
import com.google.android.material.dialog.MaterialAlertDialogBuilder

/**
 * @Author: SOHAIB AHMED
 * @Date: 17/01/2025
 * @Accounts
 *      -> https://github.com/epegasus
 *      -> https://www.linkedin.com/in/epegasus
 */

abstract class ParentDialog<T : ViewBinding>(val bindingFactory: (LayoutInflater) -> T) : ParentDialogDismissal() {

    /**
     * These properties are only valid between onCreateView and onDestroyView
     * @property binding
     *          -> after onCreateView
     *          -> before onDestroyView
     */
    private var _binding: T? = null
    protected val binding get() = _binding!!

    /**
     * These properties are only valid between onCreateView and onDestroyView
     * @property globalContext
     * @property globalActivity
     *          -> after onCreateView
     *          -> before onDestroyView
     */

    protected val globalContext by lazy { binding.root.context }
    protected val globalActivity by lazy { globalContext as Activity }


    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        _binding = bindingFactory(layoutInflater)
        (binding.root.parent as? ViewGroup)?.removeView(binding.root)

        isCancelable = false
        onDialogCreated()

        return MaterialAlertDialogBuilder(binding.root.context)
            .setView(binding.root)
            .create()
    }

    /**
     *  @since : Start code...
     */
    abstract fun onDialogCreated()

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}