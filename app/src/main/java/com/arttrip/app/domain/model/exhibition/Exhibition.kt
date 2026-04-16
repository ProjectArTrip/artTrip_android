package com.arttrip.app.domain.model.exhibition

import com.arttrip.app.core.model.enums.exhibition.ExhibitionStatus

data class Exhibition(
    val id: Int,
    val title: String,
    val posterUrl: String,
    val status: ExhibitionStatus,
    val period: String,
    val hallName: String,
    val country: String,
    val region: String,
    val isBookmarked: Boolean,
)
