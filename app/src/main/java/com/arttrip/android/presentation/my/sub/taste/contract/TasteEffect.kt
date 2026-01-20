package com.arttrip.android.presentation.my.sub.taste.contract

sealed interface TasteEffect {
    data object NavigateBack : TasteEffect

    data class ShowToastAndNavigateBack(
        val message: String,
    ) : TasteEffect

    data class ShowError(
        val message: String,
    ) : TasteEffect
}
