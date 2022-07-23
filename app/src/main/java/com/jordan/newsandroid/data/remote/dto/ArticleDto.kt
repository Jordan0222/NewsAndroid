package com.jordan.newsandroid.data.remote.dto

import com.jordan.newsandroid.domain.model.News

data class ArticleDto(
    val source: SourceDto,
    val author: String?,
    val content: String,
    val description: String,
    val publishedAt: String,
    val title: String,
    val url: String,
    val urlToImage: String
) {
    fun toArticle(): News {
        return News(
            description = description,
            title = title,
            url = url,
            urlToImage = urlToImage
        )
    }
}