package org.newsapi.newsapitestassessment.fragment.article


import android.os.Bundle
import android.util.Log
import android.view.inputmethod.EditorInfo
import androidx.fragment.app.viewModels
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import androidx.paging.LoadState
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.newsapi.common.base.BaseFragment
import org.newsapi.common.ext.setGone
import org.newsapi.common.ext.setVisible
import org.newsapi.newsapitestassessment.R
import org.newsapi.newsapitestassessment.databinding.LayoutArticleFragmentBinding
import org.newsapi.newsapitestassessment.fragment.source.SourceNewsFragmentArgs
import org.newsapi.newsapitestassessment.fragment.source.SourceNewsFragmentDirections
import org.newsapi.newsapitestassessment.view_model.ArticleNewsViewModel

@AndroidEntryPoint
class ArticleNewsFragment : BaseFragment<ArticleNewsViewModel, LayoutArticleFragmentBinding>() {
    override val vm: ArticleNewsViewModel by viewModels()
    override val layoutResourceId: Int = R.layout.layout_article_fragment
    private val adapter = ArticleNewsPagingAdapter(::getNavController)
    private val loadStateAdapter = ArticleNewsPagingStateAdapter { adapter.retry() }

    override fun initBinding(binding: LayoutArticleFragmentBinding) {
        super.initBinding(binding)
        binding.recycler.adapter = adapter.withLoadStateFooter(loadStateAdapter)

        adapter.addLoadStateListener {
            val list = adapter.snapshot()
            when (it.refresh) {
                is LoadState.Error -> {
                    binding.retryButton.setVisible()
                    binding.recycler.setGone()
                    Log.e("LoadState", "Error")
                }
                is LoadState.NotLoading -> {
                    if (list.isEmpty()) {
                        binding.retryButton.setGone()
                        binding.recycler.setGone()
                    } else {
                        binding.retryButton.setGone()
                        binding.recycler.setVisible()
                    }
                    Log.i("LoadState", "NotLoading")
                }
                is LoadState.Loading -> {
                    if (list.isEmpty()) {
                        binding.retryButton.setGone()
                        binding.recycler.setGone()
                    } else {
                        binding.retryButton.setGone()
                        binding.recycler.setVisible()
                    }
                    Log.i("LoadState", "Loading")
                }
            }
        }

        val getSources = ArticleNewsFragmentArgs.fromBundle(arguments as Bundle).selectedSources
        vm.getArticleBySource(getSources, q = "")
        binding.retryButton.setOnClickListener {
            getSources.let {
                adapter.retry()
            }
        }

        binding.etSearchArticle.setOnEditorActionListener { _, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                val textSearch = binding.etSearchArticle.text.toString()
                getSources.let {
                    vm.getArticleBySource(it, textSearch)
                }
            }
            false
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        observeLiveData()
    }

    private fun observeLiveData() = with(vm) {
        pagingData.observe(this@ArticleNewsFragment) {
            CoroutineScope(Dispatchers.Main).launch {
                adapter.submitData(it)
            }
        }
    }

    private fun getNavController(): NavController = findNavController()
}