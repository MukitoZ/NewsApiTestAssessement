package org.newsapi.newsapitestassessment.fragment.article

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.NavController
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import org.newsapi.common.binding_adapter.ImageViewBindingAdapter.loadImage
import org.newsapi.common.entity.everything.Article
import org.newsapi.newsapitestassessment.databinding.LayoutArticleItemBinding

class ArticleNewsPagingAdapter(val getNavController: () -> NavController) :
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
        getItem(position)?.let { holder.bind(it) }
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
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(data: Article) {
            binding.tvArticleTitle.text = data.title
            binding.tvArticleDescription.text = data.description
            loadImage(binding.ivImage, data.urlToImage)
            binding.root.setOnClickListener {
                val dataUrl =
                    ArticleNewsFragmentDirections.actionArticleNewsFragmentToWebViewFragment(
                        url = data.url
                    )
                getNavController().navigate(dataUrl)
            }
        }
    }
}