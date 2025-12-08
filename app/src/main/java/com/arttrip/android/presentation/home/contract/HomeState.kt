package com.arttrip.android.presentation.home.contract

import com.arttrip.android.domain.model.home.ExhibitModel

data class HomeState(
    val isLoading: Boolean = false,
    val countries: List<String> = emptyList(),
    val errorMessage: String? = null,
    val interRecommendList: List<ExhibitModel> = emptyList()
)
