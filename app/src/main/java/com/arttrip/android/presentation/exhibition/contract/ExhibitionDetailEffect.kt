package com.arttrip.android.presentation.exhibition.contract

sealed interface ExhibitionDetailEffect {
    data object NavigateBack : ExhibitionDetailEffect

    data class NavigateToWriteReview(
        val exhibitId: Int,
        val title: String,
        val hallName: String,
        val posterUrl: String?,
    ) : ExhibitionDetailEffect

    data class ShowError(
        val message: String,
    ) : ExhibitionDetailEffect

    data object RefreshReviews : ExhibitionDetailEffect
}
