package com.arttrip.app.presentation.reviewwrite.contract

sealed interface ReviewWriteEffect {
    data object NavigateBack : ReviewWriteEffect

    data class NavigateBackWithToast(
        val message: String,
    ) : ReviewWriteEffect

    data object LaunchPhotoPicker : ReviewWriteEffect

    data class ShowToast(
        val message: String,
    ) : ReviewWriteEffect
}
