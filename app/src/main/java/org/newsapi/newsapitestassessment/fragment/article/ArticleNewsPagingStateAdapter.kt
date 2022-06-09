package org.newsapi.newsapitestassessment.fragment.article

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.paging.LoadState
import androidx.paging.LoadStateAdapter
import androidx.recyclerview.widget.RecyclerView
import org.newsapi.newsapitestassessment.databinding.LayoutArticleStateItemBinding

class ArticleNewsPagingStateAdapter() :
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
            when(loadState){
                is LoadState.Error -> {
                    binding.errorContainer.visibility = View.GONE
                    binding.loadingContainer.visibility = View.VISIBLE
                }
                is LoadState.Loading -> {
                    binding.loadingContainer.visibility = View.VISIBLE
                    binding.errorContainer.visibility = View.GONE
                }
                is LoadState.NotLoading -> {
                    binding.loadingContainer.visibility = View.GONE
                    binding.errorContainer.visibility = View.GONE
                }
            }
        }
    }
}

