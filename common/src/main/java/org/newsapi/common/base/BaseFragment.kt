package org.newsapi.common.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import androidx.lifecycle.AndroidViewModel
import org.newsapi.common.BR

abstract class BaseFragment<VM : AndroidViewModel, Binding : ViewDataBinding> : Fragment() {
    abstract val vm: VM
    lateinit var binding: Binding
    abstract val layoutResourceId: Int


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate<Binding>(inflater, layoutResourceId, container, false)
        binding.setVariable(BR.vm, vm)
        binding.lifecycleOwner = this
        initBinding(binding)
        return binding.root
    }


    open fun initBinding(binding: Binding) {}
}