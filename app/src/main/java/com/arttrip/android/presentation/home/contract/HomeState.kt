package com.arttrip.android.presentation.home.contract

import com.arttrip.android.domain.model.home.ExhibitModel

data class HomeState(
    val countries: List<String> = emptyList(),
    val interRecommendExhibitList: List<ExhibitModel> = emptyList(),
    val interPersonalizedExhibitList: List<ExhibitModel> = emptyList(),
    val interScheduledExhibitList: List<ExhibitModel> = emptyList(),

    val domesticRecommendExhibitList: List<ExhibitModel> = emptyList(),
    val domesticPersonalizedExhibitList: List<ExhibitModel> = emptyList(),
    val domesticScheduledExhibitList: List<ExhibitModel> = emptyList(),
)
