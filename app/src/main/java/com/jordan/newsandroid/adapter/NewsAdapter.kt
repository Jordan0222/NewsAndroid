package com.jordan.newsandroid.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.jordan.newsandroid.R
import com.jordan.newsandroid.databinding.NewsItemBinding
import com.jordan.newsandroid.domain.model.Article
import javax.inject.Inject

class NewsAdapter @Inject constructor(): RecyclerView.Adapter<NewsAdapter.NewsViewHolder>(){

    inner class NewsViewHolder(val binding: NewsItemBinding) : RecyclerView.ViewHolder(binding.root)

    private val diffCallback = object : DiffUtil.ItemCallback<Article>() {

        override fun areItemsTheSame(oldItem: Article, newItem: Article): Boolean {
            return oldItem.url == newItem.url
        }

        override fun areContentsTheSame(oldItem: Article, newItem: Article): Boolean {
            return oldItem.hashCode() == newItem.hashCode()
        }
    }

    private val differ = AsyncListDiffer(this, diffCallback)

    var news: List<Article>
        get() = differ.currentList
        set(value) = differ.submitList(value)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewsViewHolder {
        return NewsViewHolder(
            NewsItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: NewsViewHolder, position: Int) {
        val item = news[position]

        with(holder) {
            with(news[position]) {
                binding.itemTitle.text = item.title
                binding.itemDescription.text = item.description

                binding.itemImage.load(item.urlToImage) {
                    crossfade(true)
                    placeholder(R.drawable.ic_placeholder)
                    fallback(R.drawable.ic_placeholder)
                }

                /*binding.cardView.setOnClickListener {
                    holder.itemView.findNavController().navigate(action)
                }*/
            }
        }
    }

    override fun getItemCount(): Int {
        return news.size
    }
}