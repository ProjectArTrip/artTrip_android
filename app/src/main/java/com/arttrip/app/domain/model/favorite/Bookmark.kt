package com.arttrip.app.domain.model.favorite

import com.arttrip.app.core.model.enums.exhibition.ExhibitionStatus

data class Bookmark(
    val favoriteId: Int,
    val exhibitId: Int,
    val title: String,
    val posterUrl: String,
    val status: ExhibitionStatus,
    val period: String,
    val hallName: String,
    val location: String?,
)
