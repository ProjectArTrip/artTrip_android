package com.arttrip.app.domain.model.map

import com.arttrip.app.domain.model.exhibition.Exhibition

data class ClusterExhibitPage(
    val exhibits: List<Exhibition>,
    val nextCursor: Int?,
    val hasNext: Boolean,
    val totalCount: Int,
)
