package org.newsapi.newsapitestassessment.fragment.source

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.viewModels
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import dagger.hilt.android.AndroidEntryPoint
import org.newsapi.common.base.BaseFragment
import org.newsapi.newsapitestassessment.R
import org.newsapi.newsapitestassessment.databinding.LayoutSourceFragmentBinding
import org.newsapi.newsapitestassessment.fragment.category.CategoryNewsFragmentDirections
import org.newsapi.newsapitestassessment.view_model.SourceNewsViewModel

@AndroidEntryPoint
class SourceNewsFragment : BaseFragment<SourceNewsViewModel, LayoutSourceFragmentBinding>() {
    override val vm: SourceNewsViewModel by viewModels()
    override val layoutResourceId: Int = R.layout.layout_source_fragment
    val adapter = SourceNewsAdapter(::getNavController)

    override fun initBinding(binding: LayoutSourceFragmentBinding) {
        super.initBinding(binding)
        binding.recycler.adapter = adapter
        vm.newsSourcesState.observe(this) {
            adapter.sendData(it.sources)
        }
        val getCategory = SourceNewsFragmentArgs.fromBundle(arguments as Bundle).selectedCategory
        vm.getAllSourceByCategory(category = getCategory)

        binding.etSearchSource.addTextChangedListener {
            vm.searchText = it.toString()
            vm.filterSearchSource()
            adapter.sendData(vm.listSearch)
        }
    }

    private fun getNavController(): NavController = findNavController()
}