package com.arttrip.android.presentation.exhibition.sub.reviewwrite.contract

sealed interface ReviewWriteEffect {
    data object NavigateBack : ReviewWriteEffect
}
