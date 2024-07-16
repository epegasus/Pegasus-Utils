package dev.pegasus.utils.base

import android.app.Activity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.viewbinding.ViewBinding

abstract class ParentFragment<T : ViewBinding>(val bindingFactory: (LayoutInflater) -> T) : Fragment() {

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
        _binding = bindingFactory(inflater)
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