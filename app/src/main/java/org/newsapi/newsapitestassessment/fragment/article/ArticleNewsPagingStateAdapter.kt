package org.newsapi.newsapitestassessment.fragment.article

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.paging.LoadState
import androidx.paging.LoadStateAdapter
import androidx.recyclerview.widget.RecyclerView
import org.newsapi.common.ext.setGone
import org.newsapi.common.ext.setVisible
import org.newsapi.newsapitestassessment.databinding.LayoutArticleStateItemBinding

class ArticleNewsPagingStateAdapter(
    val retry : ()->Unit
) :
    LoadStateAdapter<ArticleNewsPagingStateAdapter.ArticleNewsStateViewHolder>() {
    override fun onBindViewHolder(holder: ArticleNewsStateViewHolder, loadState: LoadState) {
        holder.bind(loadState)
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        loadState: LoadState
    ): ArticleNewsStateViewHolder =
        ArticleNewsStateViewHolder(
            LayoutArticleStateItemBinding.inflate(
                LayoutInflater.from(parent.context),null,false)
        ).apply {
            this.bind(loadState)
        }
    inner class ArticleNewsStateViewHolder(
        private val binding: LayoutArticleStateItemBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(loadState: LoadState) {
            binding.retryButtonState.setOnClickListener {
                retry()
            }
            when(loadState){
                is LoadState.Error -> {
                    binding.retryButtonState.setVisible()
                    binding.progressBarState.setGone()
                }
                is LoadState.Loading -> {
                    binding.progressBarState.setVisible()
                    binding.retryButtonState.setGone()
                }
                is LoadState.NotLoading -> {
                    binding.progressBarState.setGone()
                    binding.retryButtonState.setGone()
                }
            }
        }
    }
}

