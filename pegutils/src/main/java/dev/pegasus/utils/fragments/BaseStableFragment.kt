package dev.pegasus.utils.fragments

import android.app.Activity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding

/**
 * @Author: SOHAIB AHMED
 * @Date: 16,February,2024.
 * @Accounts
 *      -> https://github.com/epegasus
 *      -> https://linkedin.com/in/epegasus
 */
abstract class BaseStableFragment<T : ViewDataBinding>(@LayoutRes private val layoutId: Int) : BasePermissionFragment() {

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

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = DataBindingUtil.inflate(inflater, layoutId, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        onViewCreated()
    }

    /**
     *  @since : Start code...
     */
    abstract fun onViewCreated()

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}