package com.arttrip.app.presentation.home.sub.datefilterresult.contract

sealed interface DateFilterResultEffect {
    data object NavigateBack : DateFilterResultEffect

    data class ShowToast(
        val message: String,
    ) : DateFilterResultEffect
}
