package com.arttrip.android.domain.model.exhibition

import com.arttrip.android.core.model.enums.exhibition.ExhibitionStatus

data class Exhibition(
    val id: Int,
    val title: String,
    val posterUrl: String,
    val status: ExhibitionStatus,
    val period: String,
    val hallName: String,
    val place: String,
    val isBookmarked: Boolean,
)
