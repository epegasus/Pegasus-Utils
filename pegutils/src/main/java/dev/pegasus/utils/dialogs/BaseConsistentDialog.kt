package dev.pegasus.utils.dialogs

import android.app.Activity
import android.app.Dialog
import android.os.Bundle
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.DialogFragment
import com.google.android.material.dialog.MaterialAlertDialogBuilder

/**
 * @Author: SOHAIB AHMED
 * @Date: 15,May,2023.
 * @Accounts
 *      -> https://github.com/epegasus
 *      -> https://www.linkedin.com/in/epegasus
 */

abstract class BaseConsistentDialog<T : ViewDataBinding>(@LayoutRes private val layoutId: Int) : DialogFragment() {

    /**
     * These properties are only valid between onCreateView and onDestroyView
     * @property binding
     *          -> after onCreateView
     *          -> before onDestroyView
     */

    private var _binding: T? = null
    protected val binding get() = _binding!!

    protected val globalContext by lazy { binding.root.context }
    protected val globalActivity by lazy { globalContext as Activity }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        _binding = DataBindingUtil.inflate(layoutInflater, layoutId, null, false)

        // Safe Call
        if (binding.root.parent != null) {
            (binding.root.parent as ViewGroup).removeView(binding.root)
        }

        onDialogCreated()

        return MaterialAlertDialogBuilder(globalActivity).setView(binding.root).create()
    }

    abstract fun onDialogCreated()
}