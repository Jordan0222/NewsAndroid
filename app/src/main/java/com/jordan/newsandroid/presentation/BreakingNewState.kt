package com.jordan.newsandroid.presentation

import com.jordan.newsandroid.domain.model.Article

data class BreakingNewsState(
    val articleItems: List<Article> = emptyList(),
    val isLoading: Boolean = false,
    val isExpanded: Boolean = false,
    val countryAbbrev: String = "tw",
    val error: String = ""
)