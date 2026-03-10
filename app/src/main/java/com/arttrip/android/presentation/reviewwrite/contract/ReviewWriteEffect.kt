package com.arttrip.android.presentation.reviewwrite.contract

sealed interface ReviewWriteEffect {
    data object NavigateBack : ReviewWriteEffect

    data object NavigateBackWithSuccess : ReviewWriteEffect

    data object LaunchPhotoPicker : ReviewWriteEffect
}
