package org.newsapi.newsapitestassessment.fragment.category

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.fragment.findNavController
import dagger.hilt.android.AndroidEntryPoint
import org.newsapi.common.base.BaseFragment
import org.newsapi.newsapitestassessment.R
import org.newsapi.newsapitestassessment.databinding.LayoutCategoryFragmentBinding
import org.newsapi.newsapitestassessment.view_model.CategoryNewsViewModel

@AndroidEntryPoint
class CategoryNewsFragment : BaseFragment<CategoryNewsViewModel, LayoutCategoryFragmentBinding>() {
    override val vm: CategoryNewsViewModel by viewModels()
    override val layoutResourceId: Int = R.layout.layout_category_fragment
    val adapter = CategoryNewsAdapter(::getNavController)

    override fun initBinding(binding: LayoutCategoryFragmentBinding) {
        super.initBinding(binding)
        binding.recycler.adapter = adapter
        vm.data.observe(this){
            adapter.sendData(it)
        }
    }


    private fun getNavController(): NavController = findNavController()
}