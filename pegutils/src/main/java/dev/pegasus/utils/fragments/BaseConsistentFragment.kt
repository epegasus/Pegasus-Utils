package dev.pegasus.utils.fragments

import android.app.Activity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment

/**
 * @Author: SOHAIB AHMED
 * @Date: 07,April,2023.
 * @Accounts
 *      -> https://github.com/epegasus
 *      -> https://www.linkedin.com/in/epegasus
 */

abstract class BaseConsistentFragment<T : ViewDataBinding>(@LayoutRes private val layoutId: Int) : Fragment() {

    /**
     * These properties are only valid between onCreateView and onDestroyView
     * @property binding
     *          -> after onCreateView
     *          -> before onDestroyView
     */
    private var _binding: T? = null
    val binding get() = _binding!!

    private var hasInitializedRootView = false
    private var rootView: View? = null

    /**
     * These properties are only valid between onCreateView and onDestroyView
     * @property globalContext
     * @property globalActivity
     *          -> after onCreateView
     *          -> before onDestroyView
     */

    val globalContext by lazy { binding.root.context }
    val globalActivity by lazy { globalContext as Activity }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        rootView?.let {
            _binding = DataBindingUtil.bind(it)
            (it.parent as? ViewGroup)?.removeView(rootView)
            return it
        } ?: kotlin.run {
            _binding = DataBindingUtil.inflate(inflater, layoutId, container, false)
            rootView = binding.root
            return binding.root
        }
    }

    /**
     *      Use the following method in onViewCreated from escaping reinitializing of views
     *      if (!hasInitializedRootView) {
     *          hasInitializedRootView = true
     *          // Your Code...
     *      }
     */

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if (!hasInitializedRootView) {
            hasInitializedRootView = true
            onViewCreatedOneTime()
        }
        onViewCreatedEverytime()
    }

    /**
     *  @since : Write code to be called onetime...
     */
    abstract fun onViewCreatedOneTime()

    /**
     *  @since : Write code to be called everytime...
     */
    abstract fun onViewCreatedEverytime()

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onDestroy() {
        super.onDestroy()
        hasInitializedRootView = false
        rootView = null
    }
}
