package com.jordan.newsandroid.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.ViewCompositionStrategy
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.navigation.findNavController
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import coil.load
import coil.transform.CircleCropTransformation
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
                binding.composeText.apply {
                    setViewCompositionStrategy(ViewCompositionStrategy.DisposeOnViewTreeLifecycleDestroyed)
                    setContent {
                        item.description?.let {
                            Box(
                                modifier = Modifier.fillMaxSize(),
                                contentAlignment = Alignment.Center
                            ) {
                                Text(
                                    text = it,
                                    style = MaterialTheme.typography.body1,
                                    maxLines = 4,
                                    overflow = TextOverflow.Ellipsis,

                                )
                            }
                        }
                    }
                }

                binding.itemImage.load(item.urlToImage) {
                    crossfade(true)
                    placeholder(R.drawable.ic_placeholder)
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