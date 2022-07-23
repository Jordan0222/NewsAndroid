package com.jordan.newsandroid.domain.repository

import com.jordan.newsandroid.domain.model.News
import com.jordan.newsandroid.util.Resource
import kotlinx.coroutines.flow.Flow

interface ArticleRepository {

    fun getTopArticles(country: String): Flow<Resource<List<News>>>

    fun searchArticle(searchQuery: String): Flow<Resource<List<News>>>

    fun getCategoryArticles(
        country: String,
        category: String
    ): Flow<Resource<List<News>>>
}