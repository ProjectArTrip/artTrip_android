package com.arttrip.android.presentation.reviewwrite.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class ReviewWritePrefill(
    val exhibitId: Int,
    val title: String,
    val hallName: String,
    val posterUrl: String?,
    val content: String? = null,
) : Parcelable
