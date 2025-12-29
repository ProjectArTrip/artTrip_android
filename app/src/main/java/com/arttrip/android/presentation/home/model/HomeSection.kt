package com.arttrip.android.presentation.home.model

import com.arttrip.android.core.model.enums.exhibition.ExhibitionGenre
import com.arttrip.android.domain.model.exhibition.ExhibitionModel
import java.time.LocalDate

data class HomeSection(
    val recommendExhibit: SectionLoadState<List<ExhibitionModel>> = SectionLoadState.Idle,
    val personalizedList: SectionLoadState<List<ExhibitionModel>> = SectionLoadState.Idle,
    val scheduleList: Map<LocalDate, SectionLoadState<List<ExhibitionModel>>> = emptyMap(),
    val genreList: Map<ExhibitionGenre, SectionLoadState<List<ExhibitionModel>>> = emptyMap(),
)