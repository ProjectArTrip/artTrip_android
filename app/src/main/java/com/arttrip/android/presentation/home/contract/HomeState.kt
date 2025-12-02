package com.arttrip.android.presentation.home.contract

data class HomeState(
    val isLoading: Boolean = false,
    val countries: List<String> = emptyList(),
    val errorMessage: String? = null,
)
