package com.arttrip.android.presentation.exhibition.contract

sealed interface ExhibitionDetailEffect {
    data object NavigateBack : ExhibitionDetailEffect

    data class NavigateToWriteReview(
        val exhibitId: Int,
    ) : ExhibitionDetailEffect

    data class ShowError(
        val message: String,
    ) : ExhibitionDetailEffect
}
