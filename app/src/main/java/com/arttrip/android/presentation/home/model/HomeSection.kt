package com.arttrip.android.presentation.home.model

import com.arttrip.android.core.model.enums.exhibition.ExhibitionGenre
import com.arttrip.android.domain.model.exhibition.Exhibition
import java.time.LocalDate

data class HomeSection(
    val recommendExhibit: SectionLoadState<List<Exhibition>> = SectionLoadState.Idle,
    val personalizedList: SectionLoadState<List<Exhibition>> = SectionLoadState.Idle,
    val scheduleList: Map<LocalDate, SectionLoadState<List<Exhibition>>> = emptyMap(),
    val genreList: Map<ExhibitionGenre, SectionLoadState<List<Exhibition>>> = emptyMap(),
)
