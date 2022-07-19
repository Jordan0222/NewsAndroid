package com.jordan.newsandroid.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jordan.newsandroid.domain.model.Article
import com.jordan.newsandroid.domain.repository.ArticleRepository
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

    private val _newsItems = MutableLiveData<List<Article>>()
    val newsItems: LiveData<List<Article>> = _newsItems

    private var getArticlesJob: Job? = null

    init {
        getNews("tw")
    }

    /*fun onEvent(event: BreakingNewsEvent) {
        when (event) {
            is BreakingNewsEvent.CountryAbbrev -> {
                if (newsItems.value.countryAbbrev == event.countryAbbrev) {
                    return
                }
                getNews(event.countryAbbrev)
            }
            is BreakingNewsEvent.SpinnerClose -> {
                _newsItems.value = newsItems.value.copy(
                    isExpanded = false
                )
            }
            is BreakingNewsEvent.SpinnerOpen -> {
                _newsItems.value = newsItems.value.copy(
                    isExpanded = true
                )
            }
        }
    }*/

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