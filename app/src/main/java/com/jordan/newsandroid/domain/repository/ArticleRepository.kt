package com.jordan.newsandroid.domain.repository

import com.jordan.newsandroid.domain.model.Article
import com.jordan.newsandroid.util.Resource
import kotlinx.coroutines.flow.Flow

interface ArticleRepository {

    fun getTopArticles(country: String): Flow<Resource<List<Article>>>

    fun searchArticle(searchQuery: String): Flow<Resource<List<Article>>>

    fun getCategoryArticles(
        country: String,
        category: String
    ): Flow<Resource<List<Article>>>
}