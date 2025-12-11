package com.arttrip.android.presentation.exhibition.contract

sealed interface ExhibitionDetailEffect {
    data class ShowError(
        val message: String,
    ) : ExhibitionDetailEffect
}
