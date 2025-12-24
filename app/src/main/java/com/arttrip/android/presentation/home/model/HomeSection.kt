package com.arttrip.android.presentation.home.model

import com.arttrip.android.core.model.enums.exhibition.ExhibitionGenre
import com.arttrip.android.domain.model.exhibition.ExhibitionModel
import java.time.LocalDate

data class HomeSection(
    val recommendExhibit: List<ExhibitionModel> = emptyList(),
    val personalizedList: List<ExhibitionModel> = emptyList(),
    val weeklyList: Map<LocalDate, List<ExhibitionModel>> = emptyMap(),
    val genreList: Map<ExhibitionGenre, List<ExhibitionModel>> = emptyMap(),
)
