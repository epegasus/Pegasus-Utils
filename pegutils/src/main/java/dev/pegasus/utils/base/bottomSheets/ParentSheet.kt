package dev.pegasus.utils.base.bottomSheets

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.viewbinding.ViewBinding

/**
 * @Author: SOHAIB AHMED
 * @Date: 25/04/2024
 * @Accounts
 *      -> https://github.com/epegasus
 *      -> https://stackoverflow.com/users/20440272/sohaib-ahmed
 */

abstract class ParentSheet<T : ViewBinding>(val bindingFactory: (LayoutInflater) -> T) : ParentSheetDismissal() {

    private var _binding: T? = null
    protected val binding get() = _binding!!

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _binding = bindingFactory(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        onSheetCreated()
    }

    abstract fun onSheetCreated()

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}