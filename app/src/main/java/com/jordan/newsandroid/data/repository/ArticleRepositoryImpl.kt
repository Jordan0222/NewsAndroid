package com.jordan.newsandroid.data.repository

import com.jordan.newsandroid.data.remote.NewsApi
import com.jordan.newsandroid.domain.model.Article
import com.jordan.newsandroid.domain.repository.ArticleRepository
import com.jordan.newsandroid.util.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException


class ArticleRepositoryImpl(
    private val api: NewsApi
): ArticleRepository {
    override fun getTopArticles(country: String): Flow<Resource<List<Article>>> = flow {
        emit(Resource.Loading())

        try {
            val news = api.getBreakingNews(countryCode = country)
            val articles = news.articles.map { it.toArticle() }
            emit(Resource.Success(articles))
        } catch (e: IOException) {
            emit(Resource.Error(
                message = "Couldn't reach server, check your internet connection."
            ))
        } catch (e: HttpException) {
            emit(Resource.Error(
                message = "Oops, somethings went wrong!"
            ))
        }
    }

    override fun searchArticle(searchQuery: String): Flow<Resource<List<Article>>> = flow {
        emit(Resource.Loading())

        try {
            val news = api.searchForNews(searchQuery = searchQuery)
            val articles = news.articles.map { it.toArticle() }
            emit(Resource.Success(articles))
        } catch (e: IOException) {
            emit(Resource.Error(
                message = "Couldn't reach server, check your internet connection."
            ))
        } catch (e: HttpException) {
            emit(Resource.Error(
                message = "Oops, somethings went wrong!"
            ))
        }
    }

    override fun getCategoryArticles(
        country: String,
        category: String
    ): Flow<Resource<List<Article>>> = flow {
        emit(Resource.Loading())

        try {
            val news = api.getCategoryNews(countryCode = country, category = category)
            val articles = news.articles.map { it.toArticle() }
            emit(Resource.Success(articles))
        } catch (e: IOException) {
            emit(Resource.Error(
                message = "Couldn't reach server, check your internet connection."
            ))
        } catch (e: HttpException) {
            emit(Resource.Error(
                message = "Oops, somethings went wrong!"
            ))
        }
    }
}