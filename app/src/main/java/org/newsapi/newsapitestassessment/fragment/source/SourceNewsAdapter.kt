package org.newsapi.newsapitestassessment.fragment.source

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.NavController
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import org.newsapi.common.entity.sources.Source
import org.newsapi.newsapitestassessment.databinding.LayoutSourceItemBinding

class SourceNewsAdapter(
    val getNavController: () -> NavController
) : RecyclerView.Adapter<SourceNewsAdapter.SourceNewsViewHolder>() {
    private val differ = AsyncListDiffer(this, itemCallback)
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SourceNewsViewHolder {
        return SourceNewsViewHolder(
            LayoutSourceItemBinding.inflate(
                LayoutInflater.from(parent.context), null, false
            )
        )
    }

    override fun onBindViewHolder(holder: SourceNewsViewHolder, position: Int) {
        val data = differ.currentList[position]
        holder.binding.tvSourceName.text = data.name
        holder.binding.tvSourceDescription.text = data.description
        holder.binding.root.setOnClickListener {
            val dataArticle =
                SourceNewsFragmentDirections.actionSourceNewsFragmentToArticleNewsFragment(
                    selectedSources = data.id
                )
            getNavController().navigate(dataArticle)
        }
    }

    override fun getItemCount(): Int = differ.currentList.size

    fun sendData(list: List<Source>) {
        differ.submitList(list)
    }

    companion object {
        val itemCallback = object : DiffUtil.ItemCallback<Source>() {
            override fun areItemsTheSame(oldItem: Source, newItem: Source): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: Source, newItem: Source): Boolean {
                return oldItem.name == newItem.name
            }

        }
    }

    inner class SourceNewsViewHolder(
        val binding: LayoutSourceItemBinding
    ) : RecyclerView.ViewHolder(binding.root)
}