package com.arttrip.android.presentation.exhibition.sub.reviewwrite.contract

import com.arttrip.android.presentation.exhibition.sub.reviewwrite.model.ReviewWritePrefill

sealed interface ReviewWriteIntent {
    data class Initialize(
        val prefill: ReviewWritePrefill,
    ) : ReviewWriteIntent

    data object BackClicked : ReviewWriteIntent
}
