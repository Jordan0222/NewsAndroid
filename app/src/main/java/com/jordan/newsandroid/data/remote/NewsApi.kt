package com.jordan.newsandroid.data.remote

import com.jordan.newsandroid.data.remote.dto.NewsDto
import com.jordan.newsandroid.util.Constants.Companion.API_KEY
import retrofit2.http.GET
import retrofit2.http.Query

interface NewsApi {

    // BuildConfig.API_KEY

    @GET("/v2/top-headlines")
    suspend fun getBreakingNews(
        @Query("country")
        countryCode: String,
        @Query("page")
        pageNumber: Int = 1,
        @Query("apiKey")
        apiKey: String = API_KEY
    ) : NewsDto

    @GET("/v2/top-headlines")
    suspend fun getCategoryNews(
        @Query("country")
        countryCode: String,
        @Query("category")
        category: String,
        @Query("page")
        pageNumber: Int = 1,
        @Query("apiKey")
        apiKey: String = API_KEY
    ) : NewsDto

    @GET("/v2/everything")
    suspend fun searchForNews(
        @Query("q")
        searchQuery: String,
        @Query("page")
        pageNumber: Int = 1,
        @Query("apiKey")
        apiKey: String = API_KEY
    ) : NewsDto
}