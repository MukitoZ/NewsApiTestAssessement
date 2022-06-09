package org.newsapi.newsapitestassessment.fragment.category

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.NavController
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import org.newsapi.newsapitestassessment.databinding.LayoutCategoryItemBinding

class CategoryNewsAdapter(val getNavController: () -> NavController) :
    RecyclerView.Adapter<CategoryNewsAdapter.CategoryViewHolder>() {
    val differ = AsyncListDiffer(this, itemCallback)


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryViewHolder {
        return CategoryViewHolder(
            LayoutCategoryItemBinding.inflate(
                LayoutInflater.from(parent.context), null, false
            )
        )
    }

    override fun onBindViewHolder(holder: CategoryViewHolder, position: Int) {
        val data = differ.currentList[position]
        holder.binding.tvCategory.text = data
        holder.binding.root.setOnClickListener {
            val dataToSource = CategoryNewsFragmentDirections.actionCategoryFragmentToSourceNewsFragment(
                selectedCategory = data
            )
            getNavController().navigate(dataToSource)
        }
    }

    override fun getItemCount(): Int = differ.currentList.size

    fun sendData(list: List<String>) {
        differ.submitList(list)
    }

    companion object {
        val itemCallback = object : DiffUtil.ItemCallback<String>() {
            override fun areItemsTheSame(oldItem: String, newItem: String): Boolean {
                return oldItem == newItem
            }

            override fun areContentsTheSame(oldItem: String, newItem: String): Boolean {
                return oldItem == newItem
            }
        }
    }

    inner class CategoryViewHolder(val binding: LayoutCategoryItemBinding) :
        RecyclerView.ViewHolder(binding.root)
}