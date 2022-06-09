package org.newsapi.newsapitestassessment.fragment.article

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import org.newsapi.common.binding_adapter.ImageViewBindingAdapter.loadImage
import org.newsapi.common.entity.everything.Article
import org.newsapi.newsapitestassessment.databinding.LayoutArticleItemBinding

class ArticleNewsPagingAdapter :
    PagingDataAdapter<Article, ArticleNewsPagingAdapter.ArticleNewsViewHolder>(itemCallback) {
    private val differ = AsyncListDiffer(this, itemCallback)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ArticleNewsViewHolder {
        return ArticleNewsViewHolder(
            LayoutArticleItemBinding.inflate(
                LayoutInflater.from(parent.context), null, false
            )
        )
    }

    override fun onBindViewHolder(holder: ArticleNewsViewHolder, position: Int) {
        val data = differ.currentList[position]
        holder.binding.tvArticleTitle.text = data.title
        holder.binding.tvArticleDescription.text = data.description
        loadImage(holder.binding.ivImage, data.urlToImage)
    }

    override fun getItemCount(): Int = differ.currentList.size

    companion object {
        val itemCallback = object : DiffUtil.ItemCallback<Article>() {
            override fun areItemsTheSame(oldItem: Article, newItem: Article): Boolean {
                return oldItem.url == newItem.url
            }

            override fun areContentsTheSame(oldItem: Article, newItem: Article): Boolean {
                return oldItem == newItem
            }
        }
    }

    inner class ArticleNewsViewHolder(
        val binding: LayoutArticleItemBinding
    ) : RecyclerView.ViewHolder(binding.root)
}