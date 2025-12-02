package com.arttrip.android.presentation.home.contract

sealed interface HomeIntent {
    object LoadCountries : HomeIntent
    object Retry : HomeIntent
    data class CountryClicked(val name: String) : HomeIntent
}