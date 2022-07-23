package com.jordan.newsandroid.presentation.breaking_news

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jordan.newsandroid.domain.model.News
import com.jordan.newsandroid.domain.repository.ArticleRepository
import com.jordan.newsandroid.util.Country
import com.jordan.newsandroid.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class BreakingNewsViewModel @Inject constructor(
    private val repository: ArticleRepository
): ViewModel() {

    private val _newsItems = MutableLiveData<List<News>>()
    val newsItems: LiveData<List<News>> = _newsItems

    private val _countryAbbrev = MutableLiveData<String>()

    private var getArticlesJob: Job? = null

    init {
        getNews("tw")
    }

    fun onEvent(country: String) {
        val countryAbbrev = Country.getCountryAbbrev(country)
        if (_countryAbbrev.value == countryAbbrev) {
            return
        }
        getNews(countryAbbrev)
    }

    private fun getNews(countryAbbrev: String) {
        getArticlesJob?.cancel()
        getArticlesJob = viewModelScope.launch {
            repository.getTopArticles(countryAbbrev)
                .onEach { result ->
                    when (result) {
                        is Resource.Success -> {
                            val news = result.data ?: emptyList()
                            _newsItems.postValue(news)
                        }
                        is Resource.Error -> {

                        }
                        is Resource.Loading -> {

                        }
                    }
                }.launchIn(this)
        }
    }
}