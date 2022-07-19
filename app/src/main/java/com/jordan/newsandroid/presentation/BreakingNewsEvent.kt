package com.jordan.newsandroid.presentation

sealed class BreakingNewsEvent {
    data class CountryAbbrev(val countryAbbrev: String): BreakingNewsEvent()
    object SpinnerClose: BreakingNewsEvent()
    object SpinnerOpen: BreakingNewsEvent()
}
