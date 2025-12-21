package com.arttrip.android.presentation.home.model

import com.arttrip.android.domain.model.exhibition.ExhibitionModel
import com.arttrip.android.presentation.home.ExhibitGenre
import java.time.DayOfWeek

data class HomeSection(
    val recommendExhibit: List<ExhibitionModel> = emptyList(),
    val personalizedList: List<ExhibitionModel> = emptyList(),
    val weeklyList: Map<DayOfWeek, List<ExhibitionModel>> = emptyMap(),
    val genreList: Map<ExhibitGenre, List<ExhibitionModel>> = emptyMap(),
)
