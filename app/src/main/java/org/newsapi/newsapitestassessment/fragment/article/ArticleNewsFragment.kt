package org.newsapi.newsapitestassessment.fragment.article


import android.os.Bundle
import android.view.inputmethod.EditorInfo
import androidx.fragment.app.viewModels
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.newsapi.common.base.BaseFragment
import org.newsapi.newsapitestassessment.R
import org.newsapi.newsapitestassessment.databinding.LayoutArticleFragmentBinding
import org.newsapi.newsapitestassessment.fragment.source.SourceNewsFragmentArgs
import org.newsapi.newsapitestassessment.fragment.source.SourceNewsFragmentDirections
import org.newsapi.newsapitestassessment.view_model.ArticleNewsViewModel

@AndroidEntryPoint
class ArticleNewsFragment : BaseFragment<ArticleNewsViewModel, LayoutArticleFragmentBinding>() {
    override val vm: ArticleNewsViewModel by viewModels()
    override val layoutResourceId: Int = R.layout.layout_article_fragment
    private val adapter = ArticleNewsPagingAdapter()
    private val loadStateAdapter = ArticleNewsPagingStateAdapter()

    override fun initBinding(binding: LayoutArticleFragmentBinding) {
        super.initBinding(binding)
        binding.recycler.adapter = adapter.withLoadStateFooter(loadStateAdapter)
        vm.pagingData.observe(this) {
            CoroutineScope(Dispatchers.Main).launch {
                adapter.submitData(it)
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
}