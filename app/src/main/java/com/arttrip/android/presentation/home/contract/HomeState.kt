package com.arttrip.android.presentation.home.contract

import com.arttrip.android.domain.model.home.ExhibitModel
import com.arttrip.android.presentation.home.ExhibitGenre
import com.arttrip.android.presentation.home.ForeignCountry
import com.arttrip.android.presentation.home.PlaceTab

data class HomeState(
    val placeTabs: PlaceTab = PlaceTab.Foreign,
    val countryChips: ForeignCountry = ForeignCountry.Entire,
    val interRecommendExhibitList: List<ExhibitModel> = emptyList(),
    val interPersonalizedExhibitList: List<ExhibitModel> = emptyList(),
    val interScheduledExhibitList: List<ExhibitModel> = emptyList(),
    val domesticRecommendExhibitList: List<ExhibitModel> = emptyList(),
    val domesticPersonalizedExhibitList: List<ExhibitModel> = emptyList(),
    val domesticScheduledExhibitList: List<ExhibitModel> = emptyList(),
    val foreignGenreChips: List<ExhibitGenre> = List(ForeignCountry.entries.size) { ExhibitGenre.ContemporaryArt },
    val domesticGenreChips: ExhibitGenre = ExhibitGenre.ContemporaryArt,
)
