package com.arttrip.android.presentation.exhibition.contract

import com.arttrip.android.presentation.intro.contract.IntroEffect

sealed interface ExhibitionDetailEffect {
    data object NavigateBack : ExhibitionDetailEffect
    data class ShowError(
        val message: String,
    ) : ExhibitionDetailEffect
}
