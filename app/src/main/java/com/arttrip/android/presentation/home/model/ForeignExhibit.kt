package com.arttrip.android.presentation.home.model

import com.arttrip.android.domain.model.home.ExhibitModel
import com.arttrip.android.presentation.home.ExhibitGenre
import java.time.DayOfWeek

data class ForeignExhibit(
    val recommendExhibit: List<ExhibitModel> = emptyList(),
    val personalizedExhibit: List<ExhibitModel> = emptyList(),
    val scheduledExhibitList: Map<DayOfWeek, List<ExhibitModel>> = emptyMap(),
    val genreExhibitMap: Map<ExhibitGenre, List<ExhibitModel>> = emptyMap(),
)