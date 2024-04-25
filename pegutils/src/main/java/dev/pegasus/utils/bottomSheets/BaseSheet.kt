package dev.pegasus.utils.bottomSheets

import android.app.Activity
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding

/**
 * @Author: SOHAIB AHMED
 * @Date: 25/04/2024
 * @Accounts
 *      -> https://github.com/epegasus
 *      -> https://stackoverflow.com/users/20440272/sohaib-ahmed
 */

abstract class BaseSheet<T : ViewDataBinding>(@LayoutRes private val layoutId: Int) : MyBottomSheetDialog() {

    private var _binding: T? = null
    val binding get() = _binding!!

    protected val globalContext: Context by lazy { binding.root.context }
    protected val globalActivity by lazy { globalContext as Activity }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = DataBindingUtil.inflate(layoutInflater, layoutId, null, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        onSheetCreated()
    }

    abstract fun onSheetCreated()

    fun MyBottomSheetDialog.onBackPressedDispatcher(callback: () -> Unit) {
        activity?.onBackPressedDispatcher?.addCallback(this, object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                callback()
            }
        })
    }
}