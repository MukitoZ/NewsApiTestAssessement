package org.newsapi.newsapitestassessment.fragment.source

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.viewModels
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import dagger.hilt.android.AndroidEntryPoint
import org.newsapi.common.base.BaseFragment
import org.newsapi.common.entity.base_response.AppResponse
import org.newsapi.common.ext.setGone
import org.newsapi.common.ext.setVisible
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
//        vm.newsSourcesState.observe(this) {
//            adapter.sendData(it.data?.sources.orEmpty())
//        }
        val getCategory = SourceNewsFragmentArgs.fromBundle(arguments as Bundle).selectedCategory
        vm.getAllSourceByCategory(category = getCategory)

        binding.etSearchSource.addTextChangedListener {
            vm.searchText = it.toString()
            vm.filterSearchSource()
            adapter.sendData(vm.listSearch)
        }
        binding.retryButton.setOnClickListener {
            vm.getAllSourceByCategory(getCategory)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        observeLiveData()
    }

    private fun observeLiveData()= with(vm){
        newsSourcesState.observe(this@SourceNewsFragment) {
            when(it.state){
                AppResponse.SUCCESS->{
                    binding.progressBar.setGone()
                    binding.retryButton.setGone()
                    adapter.sendData(it.data?.sources.orEmpty())
                    Log.i("AppResponse","SUCCESS")
                }
                AppResponse.LOADING->{
                    binding.progressBar.setVisible()
                    binding.retryButton.setGone()
                    Log.i("AppResonse","LOADING")
                }
                AppResponse.ERROR->{
                    binding.progressBar.setGone()
                    binding.retryButton.setVisible()
                    Log.e("AppResponse", "ERROR")
                }
            }
        }
    }

    private fun getNavController(): NavController = findNavController()
}