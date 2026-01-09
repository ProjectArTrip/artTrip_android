package com.arttrip.android.presentation.reviewwrite.contract

sealed interface ReviewWriteEffect {
    data object NavigateBack : ReviewWriteEffect

    data object LaunchPhotoPicker : ReviewWriteEffect
}
