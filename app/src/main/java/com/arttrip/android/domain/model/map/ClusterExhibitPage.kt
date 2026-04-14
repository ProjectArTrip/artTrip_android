package com.arttrip.android.domain.model.map

import com.arttrip.android.domain.model.exhibition.Exhibition

data class ClusterExhibitPage(
    val exhibits: List<Exhibition>,
    val nextCursor: Int?,
    val hasNext: Boolean,
    val totalCount: Int,
)
