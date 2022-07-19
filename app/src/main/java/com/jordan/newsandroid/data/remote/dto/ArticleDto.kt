package com.jordan.newsandroid.data.remote.dto

import com.jordan.newsandroid.domain.model.Article

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
    fun toArticle(): Article {
        return Article(
            description = description,
            title = title,
            url = url,
            urlToImage = urlToImage
        )
    }
}