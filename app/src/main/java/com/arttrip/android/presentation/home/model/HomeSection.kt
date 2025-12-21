package com.arttrip.android.presentation.home.model

import com.arttrip.android.domain.model.home.ExhibitModel
import com.arttrip.android.presentation.home.ExhibitGenre
import java.time.DayOfWeek

data class HomeSection(
    val recommendExhibit: List<ExhibitModel> = emptyList(),
    val personalizedList: List<ExhibitModel> = emptyList(),
    val weeklyList: Map<DayOfWeek, List<ExhibitModel>> = emptyMap(),
    val genreList: Map<ExhibitGenre, List<ExhibitModel>> = emptyMap(),
)
