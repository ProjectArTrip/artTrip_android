package com.arttrip.android.presentation.exhibition.contract

sealed interface ExhibitionDetailIntent {
    data class Initialize(
        val exhibitId: Int,
    ) : ExhibitionDetailIntent

    data object BackClicked : ExhibitionDetailIntent
}
