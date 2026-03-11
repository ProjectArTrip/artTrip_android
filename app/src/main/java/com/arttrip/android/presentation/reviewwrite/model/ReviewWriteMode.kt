package com.arttrip.android.presentation.reviewwrite.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

sealed interface ReviewWriteMode : Parcelable {
    @Parcelize
    data class Create(
        val prefill: ReviewCreatePrefill,
    ) : ReviewWriteMode

    @Parcelize
    data class Edit(
        val reviewId: Int,
    ) : ReviewWriteMode
}

@Parcelize
data class ReviewCreatePrefill(
    val exhibitId: Int,
    val title: String,
    val hallName: String,
    val posterUrl: String?,
) : Parcelable
